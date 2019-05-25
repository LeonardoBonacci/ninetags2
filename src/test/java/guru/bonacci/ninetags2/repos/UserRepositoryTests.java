package guru.bonacci.ninetags2.repos;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import guru.bonacci.ninetags2.domain._User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;


	@Before
	public void setUp() {
		repo.deleteAll();
	
		_User gamma = _User.builder().name("Gamma").build();
		_User beta = _User.builder().name("Beta").build();
		beta.addFollows(gamma);
		_User alpha = _User.builder().name("Alpha").build();
		alpha.addFollows(beta);
		
		repo.saveAll(Arrays.asList(alpha, beta, gamma));
	}

	@Test
	public void testFindByName() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		_User result = repo.findByName(name).get();
		assertNotNull(result);
	}
	
	@Test
	public void testFindByFollowers() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		List<_User> results = repo.findFollowers(name).get();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals("Beta", results.get(0).getName());
	}
}
