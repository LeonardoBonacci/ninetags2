package guru.bonacci.ninetags2.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.neo4j.graphdb.security.AuthorizationViolationException;
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
	public CompletableFuture<Optional<_User>> getByName(String name) {
		if (!name.equalsIgnoreCase(context.getAuthentication()))
			throw new AuthorizationViolationException("No permission..");
		
		CompletableFuture<Optional<_User>> user = repo.findByName(name);
		return user.whenComplete((result, ex) -> result.ifPresent(us -> log.info("found user " + us)));
	}

	@Transactional(readOnly = true)
	public CompletableFuture<List<_User>> getFollowed() {
		CompletableFuture<List<_User>> followed = repo.getFollowed(context.getAuthentication());
		return followed.whenComplete((results, ex) -> results.forEach(result -> log.info("found followed user " + result)));
	}

	@Transactional(readOnly = true)
	public CompletableFuture<Page<_User>> getFollowed(final Pageable page) {
		CompletableFuture<Page<_User>> followed = repo.getFollowed(context.getAuthentication(), page);
		return followed.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found followed user " + result)));
	}

}
