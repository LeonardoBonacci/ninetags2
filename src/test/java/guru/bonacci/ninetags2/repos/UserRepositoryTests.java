package guru.bonacci.ninetags2.repos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.ninetags2.domain._User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;


	@Before
	public void setUp() {
		repo.deleteAll();
	
		_User alpha = _User.builder().name("Alpha").build();
		_User beta = _User.builder().name("Beta").build();
		_User gamma = _User.builder().name("Gamma").build();
		_User delta = _User.builder().name("Delta").build();
		_User epsilon = _User.builder().name("Epsilon").build();
		_User zeta = _User.builder().name("Zeta").build();
		_User eta = _User.builder().name("Eta").build();
		_User theta = _User.builder().name("Theta").build();
		_User iota = _User.builder().name("Iota").build();
		_User kappa = _User.builder().name("Kappa").build();
		_User lambda = _User.builder().name("Lambda").build();
		_User mu = _User.builder().name("Mu").build();
		_User nu = _User.builder().name("Nu").build();

		alpha.addFollows(beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu);
		beta.addFollows(gamma);
		beta.addFollows(alpha);
		repo.saveAll(Arrays.asList(alpha, beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu, nu));
	}

	@Test
	public void testFindByName() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		_User result = repo.findByNameIgnoreCase(name).get();
		assertNotNull(result);
	}

	@Test
	public void testFindAllByName() throws InterruptedException, ExecutionException {
		List<_User> results = repo.findByNameIn(Arrays.asList("Alpha", "Beta"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void testGetFollowed() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		List<_User> results = repo.getFollowers(name).get();
		assertEquals(1, results.size());
	}
	
	@Test
	public void testGetFollowedPaged() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		Page<_User> results = repo.getFollowed(name, PageRequest.of(0, 9)).get();
		assertEquals(9, results.getNumberOfElements());
		
		results = repo.getFollowed(name, PageRequest.of(1, 9)).get();
		assertEquals(2, results.getNumberOfElements());

		results = repo.getFollowed(name, PageRequest.of(2, 9)).get();
		assertEquals(0, results.getNumberOfElements());
	}

}
