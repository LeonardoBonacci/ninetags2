package guru.bonacci.ninetags2.services;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.beans.BeanUtils.copyProperties;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.neo4j.graphdb.security.AuthorizationViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.SharedWithRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.repos.UserRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
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
	private final FakeSecurityContext context; 


	@Transactional(readOnly = true)
	public CompletableFuture<List<Share>> findByTitle(String title) {
		val shares = shareRepo.findByTitleContaining(title);
		return shares.whenComplete((results, ex) -> results.forEach(s -> log.info("found share " + s)));
	}

	
	@Transactional
	public CompletableFuture<Long> insert(final Share.ShareBuilder shareBuilder, final List<Topic> topics) {
		_User fromMe = context.getTheUser();
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(fromMe).about(stream(savedTopics.spliterator(), false).collect(toList())).build();
		return completedFuture(shareRepo.save(share).getId());
	}

	
	@Transactional
	public CompletableFuture<Long> insertPrivate(final Share.ShareBuilder shareBuilder, final List<Topic> topics) {
		_User fromMe = context.getTheUser();
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(fromMe).about(stream(savedTopics.spliterator(), false).collect(toList())).build();
		sharedWithRepo.save(SharedWith.builder().share(share).with(fromMe).build());
		return completedFuture(shareRepo.save(share).getId());
	}

	
	@Transactional
	public CompletableFuture<Long> insertDirected(final Share.ShareBuilder shareBuilder, final List<Topic> topics, final List<String> toUsernames) {
		_User fromMe = context.getTheUser();
		CompletableFuture<List<_User>> toUs = userRepo.findByNameIn(toUsernames);
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(fromMe).about(stream(savedTopics.spliterator(), false).collect(toList())).build();
		toUs.whenComplete((result, ex)  -> result.forEach(toMe -> sharedWithRepo.save(SharedWith.builder().share(share).with(toMe).build())));
		return completedFuture(shareRepo.save(share).getId());
	}

	
	@Transactional
	public CompletableFuture<Share> update(final Share.ShareBuilder shareBuilder, final List<Topic> topics) {
		_User fromMe = context.getTheUser();
		
		val savedTopics = topicRepo.saveAll(topics);
		val share = shareBuilder.by(fromMe).about(stream(savedTopics.spliterator(), false).collect(toList())).build();
		
		shareRepo.findById(share.getId())
				.ifPresent(persisted -> {
					if (!persisted.getBy().getName().equalsIgnoreCase(context.getAuthentication()))
						throw new AuthorizationViolationException("You cannot change what is not yours..");

					copyProperties(share, persisted);
					shareRepo.save(persisted);
				});
		return shareRepo.findByTitle(share.getTitle()).whenComplete((result, ex) -> log.info("updated share " + result));
	}


	@Transactional
	public CompletableFuture<Void> delete(final Long id) {
		shareRepo.deleteById(id);
		return completedFuture(null);
	}
}