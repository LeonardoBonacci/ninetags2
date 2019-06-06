package guru.bonacci.ninetags2.services;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Interests;
import guru.bonacci.ninetags2.domain.Topic;
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
public class TopicService {

	private final UserRepository userRepo;
	private final TopicRepository topicRepo;
	private final FakeSecurityContext context;
	

	@Transactional(readOnly = true)
	public CompletableFuture<List<Topic>> retrieveByName(@NonNull final String topic) {
		val topics = topicRepo.findByNameContaining(topic);
		return topics.whenComplete((results, ex) -> results.stream().forEach(result -> log.debug("found topic " + result)));
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<Topic>> retrieveFollowed(@NonNull final Pageable pageRequest) {
		val followers = topicRepo.getFollowed(context.getAuthentication(), pageRequest);
		return followers.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found followed topic " + result)));
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<Topic>> retrieveMostSharingByUser(final String username, final Pageable pageRequest) {
		val sharedTopics = topicRepo.getMostSharingOnUser(username, pageRequest);
		return sharedTopics.whenComplete((results, ex) -> results.forEach(result -> log.info("found active topic " + result)));
	}

	
	@Transactional
	public CompletableFuture<Void> prioritize(@NonNull final Set<Interests> interests) {
		userRepo.findById(context.getAuthentication()).ifPresent(user -> {
			user.addInterests(interests.stream().map(i -> { i.setFollower(user); return i; }).toArray(Interests[]::new));
			userRepo.save(user);
		});;
		
		return CompletableFuture.completedFuture(null);
	}
	
	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<Topic>> retrieveHotTopics(final Pageable pageRequest) {
		val hotties = topicRepo.getMostLikedTopicsToday(pageRequest);
		return hotties.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found hot topic " + result)));
	}
	

	@Transactional(readOnly = true)
	public CompletableFuture<List<Topic>> recommend() {
		val recommendations = topicRepo.recommendTopicsToFollow(context.getAuthentication());
		return recommendations.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("recommend topic " + result)));
	}
}
