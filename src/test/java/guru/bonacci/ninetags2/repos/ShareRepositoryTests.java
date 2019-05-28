package guru.bonacci.ninetags2.repos;

import static org.junit.Assert.assertEquals;
import static java.util.Arrays.asList;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ShareRepositoryTests {

	@Autowired
	private ShareRepository repo;


	@Before
	public void setUp() {
		repo.deleteAll();
	
		Share s1 = Share.builder().title("foo").build();
		Share s2 = Share.builder().title("foobar").build();

		repo.saveAll(asList(s1, s2));
	}

	@Test
	public void testFindByTitle() throws InterruptedException, ExecutionException {
		assertEquals(1, repo.findByTitleContaining("foobar").get().size());
		assertEquals(2, repo.findByTitleContaining("foo").get().size());

	}
}