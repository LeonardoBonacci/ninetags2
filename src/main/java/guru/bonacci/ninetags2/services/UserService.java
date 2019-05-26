package guru.bonacci.ninetags2.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.UserRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repo;
	private final FakeSecurityContext context;
	

	@Transactional(readOnly = true)
	public CompletableFuture<_User> getByName(String name) {
		CompletableFuture<_User> user = repo.findByName(name);
		return user.whenComplete((result, ex) -> log.info("found user " + result));
	}
	
	@Transactional(readOnly = true)
	public CompletableFuture<List<_User>> getFollowers() {
		CompletableFuture<List<_User>> followers = repo.getFollowers(context.getAuthentication());
		return followers.whenComplete((results, ex) -> results.forEach(result -> log.info("found follower " + result)));
	}

	@Transactional(readOnly = true)
	public CompletableFuture<Page<_User>> getFollowers(final Pageable page) {
		CompletableFuture<Page<_User>> followers = repo.getFollowers(context.getAuthentication(), page);
		return followers.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found follower " + result)));
	}

}
