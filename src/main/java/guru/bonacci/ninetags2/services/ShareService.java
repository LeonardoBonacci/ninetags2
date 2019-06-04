package guru.bonacci.ninetags2.services;


import static com.google.common.collect.Sets.newHashSet;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.beans.BeanUtils.copyProperties;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.neo4j.graphdb.security.AuthorizationViolationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Forwards;
import guru.bonacci.ninetags2.domain.Likes;
import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.events.CreationEvent;
import guru.bonacci.ninetags2.repos.ForwardsRepository;
import guru.bonacci.ninetags2.repos.LikesRepository;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.SharedWithRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.repos.UserRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareService {

	private final SharedWithRepository sharedWithRepo;
	private final ShareRepository shareRepo;
	private final TopicRepository topicRepo;
	private final UserRepository userRepo;
	private final LikesRepository likesRepo;
	private final ForwardsRepository forwardsRepo;

	private final FakeSecurityContext context; 
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional(readOnly = true)
	public CompletableFuture<List<Share>> findByTitle(@NonNull String title) {
		val shares = shareRepo.findByTitleContaining(title);
		return shares.whenComplete((results, ex) -> results.forEach(s -> log.info("found share " + s)));
	}

	
	@Transactional
	public CompletableFuture<Long> insert(@NonNull final Share.ShareBuilder shareBuilder, @NonNull final List<Topic> topics) {
		_User user = context.getTheUser();
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(user).about(stream(savedTopics.spliterator(), false).collect(toSet())).build();
		val id = completedFuture(shareRepo.save(share).getId());

		applicationEventPublisher.publishEvent(new CreationEvent<Share>(share, "private insert"));
		return id;
	}

	
	@Transactional
	public CompletableFuture<Long> insertPrivate(@NonNull final Share.ShareBuilder shareBuilder, @NonNull final List<Topic> topics) {
		_User user = context.getTheUser();
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(user).about(stream(savedTopics.spliterator(), false).collect(toSet())).build();
		sharedWithRepo.save(SharedWith.builder().share(share).with(user).build());
		val id = completedFuture(shareRepo.save(share).getId());

		applicationEventPublisher.publishEvent(new CreationEvent<Share>(share, "private insert"));
		return id;
	}

	
	@Transactional
	public CompletableFuture<Long> insertDirected(@NonNull final Share.ShareBuilder shareBuilder, @NonNull final List<Topic> topics, @NonNull final List<String> toUsernames) {
		_User user = context.getTheUser();
		List<_User> toUs = userRepo.findByNameIn(toUsernames);
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(user).about(stream(savedTopics.spliterator(), false).collect(toSet())).build();
		toUs.forEach(toMe -> sharedWithRepo.save(SharedWith.builder().share(share).with(toMe).build()));

		val id = completedFuture(shareRepo.save(share).getId());
		applicationEventPublisher.publishEvent(new CreationEvent<Share>(share, "directed insert"));
		return id;
	}

	
	@Transactional
	public CompletableFuture<Share> update(@NonNull final Share.ShareBuilder shareBuilder, @NonNull final List<Topic> topics) {
		val user = context.getTheUser();
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(user).about(stream(savedTopics.spliterator(), false).collect(toSet())).build();
		
		shareRepo.findById(share.getId())
				.ifPresent(persisted -> {
					if (!persisted.getBy().getName().equalsIgnoreCase(context.getAuthentication()))
						throw new AuthorizationViolationException("You cannot change what is not yours..");

					Long creation = persisted.getTime();
					copyProperties(share, persisted);
					persisted.setTime(creation);
					shareRepo.save(persisted);
				});
		return shareRepo.findByTitle(share.getTitle()).whenComplete((result, ex) -> log.info("updated share " + result));
	}


	@Transactional
	public CompletableFuture<Void> delete(@NonNull final Long id) {
		shareRepo.findById(id)
			.ifPresent(persisted -> {
				if (!persisted.getBy().getName().equalsIgnoreCase(context.getAuthentication()))
					throw new AuthorizationViolationException("You cannot delete what is not yours..");
			});

		shareRepo.deleteById(id);
		return completedFuture(null);
	}
	

	@Transactional
	public CompletableFuture<Void> like(@NonNull  final Long shareId) {
		val user = context.getTheUser();

		shareRepo.findById(shareId).ifPresent(share -> {
			if (share.getBy().getName().equalsIgnoreCase(context.getAuthentication()))
				throw new AuthorizationViolationException("You cannot like yourself..");

			likesRepo.save(Likes.builder().share(share).user(user).build()); // double liking is just an update
		});
		
		return completedFuture(null);
	}
	
	
	@Transactional
	public CompletableFuture<Long> forShare(@NonNull final Long shareId, @NonNull List<Topic> newTopics) {
		val user = context.getTheUser();
		val savedNewTopics = topicRepo.saveAll(newTopics);

		final Long forShareId = shareRepo.findById(shareId).map(share -> {
			// fill up to max. 9 tags
			share.getAbout().addAll(newHashSet(savedNewTopics));
			if (share.getAbout().size() > 9)
				throw new AuthorizationViolationException("greed greed greed..");
				
			Share forShare = new Share();
			copyProperties(share, forShare);

			forShare.setId(null);
			forShare.setTitle("FS: " + share.getTitle());
			forShare.setTime(null);
			forShare.setBy(user);
			
			Long newId = shareRepo.save(forShare).getId();
			forwardsRepo.save(Forwards.builder().share(share).forShare(forShare).build(), 0);
			applicationEventPublisher.publishEvent(new CreationEvent<Share>(forShare, "forshare insert"));
			return newId;
		}).orElse(null);
		
		return completedFuture(forShareId);
	}
}