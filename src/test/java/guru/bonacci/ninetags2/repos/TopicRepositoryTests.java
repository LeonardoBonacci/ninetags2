package guru.bonacci.ninetags2.repos;

import static org.junit.Assert.assertEquals;

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

import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicRepositoryTests {

	@Autowired
	private TopicRepository topicRepo;

	@Autowired
	private UserRepository userRepo;


	@Before
	public void setUp() {
		userRepo.deleteAll();
		topicRepo.deleteAll();
	
		_User alpha = _User.builder().name("Alpha").build();

		Topic culture = Topic.builder().name("Culture").build();
		Topic cultural = Topic.builder().name("Cultural").build();
		Topic cultuurbarbaar = Topic.builder().name("Cultuurbarbaar").build();
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
		topicRepo.saveAll(Arrays.asList(culture, cultural, cultuurbarbaar, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports, dance));

		alpha.addInterests(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports);
		userRepo.save(alpha);
	}

	@Test
	public void testLikeContaining() throws InterruptedException, ExecutionException {
		List<Topic> results = topicRepo.findByNameContaining("Cult").get(); //ignore case?
		assertEquals(3, results.size());
	}

	@Test
	public void testGetFollowedPaged() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		Page<Topic> results = topicRepo.getFollowed(name, PageRequest.of(0, 9)).get();
		assertEquals(9, results.getNumberOfElements());
		
		results = topicRepo.getFollowed(name, PageRequest.of(1, 9)).get();
		assertEquals(1, results.getNumberOfElements());
		assertEquals("Sports", results.get().findFirst().get().getName()); //last added has highest prio

		results = topicRepo.getFollowed(name, PageRequest.of(2, 9)).get();
		assertEquals(0, results.getNumberOfElements());
	}

}
