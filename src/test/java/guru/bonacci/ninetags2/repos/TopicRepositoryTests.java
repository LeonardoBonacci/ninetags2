package guru.bonacci.ninetags2.repos;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TopicRepositoryTests {

	@Autowired
	private TopicRepository trepo;

	@Autowired
	private UserRepository repo;


	@Before
	public void setUp() {
		repo.deleteAll();
	
		_User alpha = _User.builder().name("Alpha").build();

		Topic culture = Topic.builder().name("Culture").build();
		Topic cooking = Topic.builder().name("Cooking").build();
		Topic hobbies = Topic.builder().name("Hobbies").build();
		Topic literature = Topic.builder().name("Literature").build();
		Topic art = Topic.builder().name("Art").build();
		Topic entertainment = Topic.builder().name("Entertainment").build();
		Topic fiction = Topic.builder().name("Fiction").build();
		Topic game = Topic.builder().name("Game").build();
		Topic poetry = Topic.builder().name("Poetry").build();
		Topic sports = Topic.builder().name("Sports").build();
		Topic dance = Topic.builder().name("Dance").build();
		trepo.saveAll(Arrays.asList(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports, dance));

		alpha.addInterests(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports);
		repo.save(alpha);
	}

	@Test
	public void testGetFollowedPaged() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		Page<Topic> results = trepo.getFollowed(name, PageRequest.of(0, 9)).get();
		assertEquals(9, results.getNumberOfElements());
		assertEquals(10, results.getTotalElements());
		
		results = trepo.getFollowed(name, PageRequest.of(1, 9)).get();
		assertEquals(1, results.getNumberOfElements());
		assertEquals("Sports", results.get().findFirst().get().getName()); //last added has highest prio

		results = trepo.getFollowed(name, PageRequest.of(2, 9)).get();
		assertEquals(0, results.getNumberOfElements());
		
	}

}
