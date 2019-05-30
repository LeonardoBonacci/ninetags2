package guru.bonacci.ninetags2.repos;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.ninetags2.domain.Share;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShareRepositoryTests {

	@Autowired
	private ShareRepository shareRepo;


	@Before
	public void setUp() {
		shareRepo.deleteAll();
	
		Share s1 = Share.builder().title("foo").build();
		Share s2 = Share.builder().title("foobar").build();

		shareRepo.saveAll(asList(s1, s2));
	}

	@Test
	public void testFindByTitleContaining() throws InterruptedException, ExecutionException {
		assertEquals(1, shareRepo.findByTitleContaining("foobar").get().size());
		assertEquals(2, shareRepo.findByTitleContaining("foo").get().size());
	}
	
	@Test
	public void testFindOneByTitle() throws InterruptedException, ExecutionException {
		Share s = shareRepo.save(Share.builder().title("bar").build());
		assertNotNull(shareRepo.findByTitle(s.getTitle()).get());
	}

}
