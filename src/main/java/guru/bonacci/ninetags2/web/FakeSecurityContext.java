package guru.bonacci.ninetags2.web;

import java.util.NoSuchElementException;

import org.neo4j.graphdb.security.AuthorizationViolationException;

import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
public class FakeSecurityContext {

	
	private final UserRepository userRepo;

	@Getter
	@Setter
	String authentication;
	

	public _User getTheUser() {
		try {
			return userRepo.findByNameIgnoreCase(authentication).get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new AuthorizationViolationException("No permission..");
		} 
	}
}
