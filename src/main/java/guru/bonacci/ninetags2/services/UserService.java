package guru.bonacci.ninetags2.services;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.neo4j.graphdb.security.AuthorizationViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Follows;
import guru.bonacci.ninetags2.domain._User;
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
public class UserService {

	private final UserRepository userRepo;
	private final TopicRepository topicRepo;
	private final FakeSecurityContext context;
	

	@Transactional(readOnly = true)
	public CompletableFuture<Optional<_User>> retrieveByName(String name) {
		if (!name.equalsIgnoreCase(context.getAuthentication()))
			throw new AuthorizationViolationException("No permission..");
		
		val user = userRepo.findByNameIgnoreCase(name);
		return CompletableFuture.completedFuture(user);
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<List<_User>> retrieveFollowed() {
		val followed = userRepo.getFollowers(context.getAuthentication());
		return followed.whenComplete((results, ex) -> results.forEach(result -> log.info("found follower " + result)));
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<_User>> retrieveFollowed(final Pageable pageRequest) {
		val followed = userRepo.getFollowed(context.getAuthentication(), pageRequest);
		return followed.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found followed user " + result)));
	}

	
	@Transactional
	public CompletableFuture<Void> follows(final String followMe) {
		_User follower = context.getTheUser();

		userRepo.findByNameIgnoreCase(followMe).ifPresent(followed -> {
			follower.addFollows(followed);
			userRepo.save(follower);
		});
		return completedFuture(null);
	}

	
	@Transactional
	public CompletableFuture<Void> unfollows(final String doNotfollowMe) {
		_User follower = context.getTheUser();

		userRepo.findByNameIgnoreCase(doNotfollowMe).ifPresent(unfollowed -> {
			follower.deleteFollows(unfollowed);
			userRepo.save(follower);
		});	
		return completedFuture(null);
	}


	@Transactional
	public CompletableFuture<Void> interests(final String followMe) {
		_User follower = context.getTheUser();

		topicRepo.findByNameIgnoreCase(followMe).ifPresent(followed -> {
			follower.addTopics(followed);
			userRepo.save(follower);
		});	
		return completedFuture(null);
	}
	
	
	@Transactional
	public CompletableFuture<Void> uninterests(final String doNotfollowMe) {
		_User follower = context.getTheUser();

		topicRepo.findByNameIgnoreCase(doNotfollowMe).ifPresent(unfollowed -> {
			follower.deleteTopics(unfollowed);
			userRepo.save(follower);
		});	
		return completedFuture(null);
	}
	
	@Transactional
	public CompletableFuture<Void> prioritize(@NonNull final Set<Follows> follows) {
		userRepo.findByNameIgnoreCase(context.getAuthentication()).ifPresent(user -> {
			user.addFollows(follows.stream().map(f -> { f.setFollower(user); return f; }).toArray(Follows[]::new));
			userRepo.save(user);
		});;
		
		return CompletableFuture.completedFuture(null);
	}
}
