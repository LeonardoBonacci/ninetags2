package guru.bonacci.ninetags2.repos;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import lombok.val;
import lombok.var;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SharedWithRepositoryTests {

	@Autowired
	private SharedWithRepository sharedWithRepo;

	@Autowired
	private ShareRepository shareRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TopicRepository topicRepo;

	
	@Before
	public void setUp() {
		sharedWithRepo.deleteAll();
		shareRepo.deleteAll();
		userRepo.deleteAll();
		topicRepo.deleteAll();
	
		val alpha = _User.builder().name("Alpha").build();
		userRepo.save(alpha);
		val beta = _User.builder().name("Beta").build();
		val gamma = _User.builder().name("Gamma").build();

		val cooking = Topic.builder().name("Cooking").build();
		val story = Topic.builder().name("Story").build();
		topicRepo.saveAll(asList(cooking, story));
		
		val sculture = Share.builder().title("On Culture").by(alpha).build();
		val scooking = Share.builder().title("On Cooking").by(alpha).about(singletonList(cooking)).build();
		val shobbies = Share.builder().title("On Hobbies").by(alpha).build();
		val sliterature = Share.builder().title("On Literature").by(alpha).build();
		val sart = Share.builder().title("On Art").by(alpha).build();
		val sentertainment = Share.builder().title("On Entertainment").by(alpha).build();
		val sfiction = Share.builder().title("On Fiction").by(alpha).about(singletonList(story)).build();
		val sgame = Share.builder().title("On Game").by(alpha).build();
		val spoetry = Share.builder().title("On Poetry").by(alpha).build();
		val ssports = Share.builder().title("On Sports").by(alpha).build();
		val sdance = Share.builder().title("On Dance").by(alpha).build();
		shareRepo.saveAll(asList(sculture, scooking, shobbies, sliterature, sart, sentertainment, sfiction, sgame, spoetry, ssports, sdance));
		userRepo.save(alpha);
		
		val swculture = SharedWith.builder().share(sculture).with(alpha).build();
		val swcooking = SharedWith.builder().share(scooking).with(alpha).build();
		val swhobbies = SharedWith.builder().share(shobbies).with(alpha).build();

		val swliterature = SharedWith.builder().share(sliterature).with(beta).build();
		val swart = SharedWith.builder().share(sart).with(beta).build();
		val swentertainment1 = SharedWith.builder().share(sentertainment).with(beta).build();
		val swentertainment2 = SharedWith.builder().share(sentertainment).with(gamma).build();
		val swfiction1 = SharedWith.builder().share(sfiction).with(beta).build();
		val swfiction2 = SharedWith.builder().share(sfiction).with(gamma).build();
		val swgame = SharedWith.builder().share(sgame).with(gamma).build();
		val swpoetry = SharedWith.builder().share(spoetry).with(gamma).build();

		sharedWithRepo.saveAll(asList(swculture, swcooking, swhobbies, swliterature, swart, swentertainment1, swentertainment2, swfiction1, swfiction2, swgame, swpoetry));
	}

	@Test
	public void testGetPrivateShares() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		var results = shareRepo.getPrivateShares(name, PageRequest.of(0, 2)).get();
		assertEquals(2, results.getNumberOfElements());
		
		results = shareRepo.getPrivateShares(name, PageRequest.of(1, 2)).get();
		assertEquals(1, results.getNumberOfElements());

		results = shareRepo.getPrivateShares(name, PageRequest.of(2, 2)).get();
		assertEquals(0, results.getNumberOfElements());
	}
	
	@Test
	public void testGetSentDirectedShares() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		var results = shareRepo.getSentDirectedShares(name, PageRequest.of(0, 4)).get();
//		assertEquals(3, results.getNumberOfElements());
//FIXME		race condition
		results = shareRepo.getSentDirectedShares(name, PageRequest.of(1, 4)).get();
//		assertEquals(4, results.getNumberOfElements());

		results = shareRepo.getSentDirectedShares(name, PageRequest.of(2, 4)).get();
		assertEquals(0, results.getNumberOfElements());
	}

	@Test
	public void testGetReceivedDirectedShares() throws InterruptedException, ExecutionException {
		String name = "Beta";
		var results = shareRepo.getReceivedDirectedShares(name, PageRequest.of(0, 3)).get();
		assertEquals(3, results.getNumberOfElements());

		name = "Gamma";
		results = shareRepo.getReceivedDirectedShares(name, PageRequest.of(0, 3)).get();
		assertEquals(3, results.getNumberOfElements());
	}
}
