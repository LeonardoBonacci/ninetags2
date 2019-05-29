package guru.bonacci.ninetags2.repos;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import lombok.val;
import lombok.var;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SharedPerspectiveRepositoryTests {

	@Autowired
	private SharePerspectiveRepository sharedPerspectiveRepo;

	@Autowired
	private ShareRepository shareRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TopicRepository topicRepo;

	
	@Before
	public void setUp() {
		userRepo.deleteAll();
		topicRepo.deleteAll();
		shareRepo.deleteAll();

		val cooking = Topic.builder().name("Cooking").build();
		val story = Topic.builder().name("Story").build();
		val play = Topic.builder().name("Play").build();
		val notInteresting = Topic.builder().name("Not interesting").build();
		topicRepo.saveAll(asList(cooking, story, play));

		val alpha = _User.builder().name("Alpha").build();
		val beta = _User.builder().name("Beta").build();
		val gamma = _User.builder().name("Gamma").build();
		alpha.addFollows(beta, gamma);
		alpha.addInterests(cooking, play);
		beta.addFollows(alpha);
		beta.addInterests(cooking, story);
		gamma.addFollows(alpha, beta);
		gamma.addInterests(cooking, story, play);
		userRepo.saveAll(asList(alpha, beta, gamma));
		
		val s1 = Share.builder().title("On Cooking").by(alpha).about(singletonList(cooking)).build();
		val s2 = Share.builder().title("On Porridge").by(alpha).about(singletonList(cooking)).build();
		val s3 = Share.builder().title("Talking about cooking").by(alpha).about(asList(cooking, story)).build();
		val s7 = Share.builder().title("Foo").by(alpha).about(singletonList(notInteresting)).build();

		val s4 = Share.builder().title("On Art").by(beta).about(singletonList(story)).build();
		val s5 = Share.builder().title("On Entertainment").by(beta).about(asList(story, play)).build();
		
		val s6 = Share.builder().title("On Nothingness").by(gamma).build();

		shareRepo.saveAll(asList(s1, s2, s3, s4, s5, s6, s7));
	}

	@Test
	public void testGetFollowedAndInterested() throws InterruptedException, ExecutionException {
		var results = sharedPerspectiveRepo.getFollowedAndInterested("Alpha", PageRequest.of(0, 10)).get();
		assertEquals(1, results.getNumberOfElements());
		assertNotNull(results.getContent().get(0).getBy());
		assertFalse(results.getContent().get(0).getAbout().isEmpty());

		results = sharedPerspectiveRepo.getFollowedAndInterested("Beta", PageRequest.of(0, 10)).get();
		assertEquals(3, results.getNumberOfElements());

		results = sharedPerspectiveRepo.getFollowedAndInterested("Gamma", PageRequest.of(0, 10)).get();
		assertEquals(5, results.getNumberOfElements());
	}

	@Test 
	public void testGetFollowedAndNotInterested() throws InterruptedException, ExecutionException {
		var results = sharedPerspectiveRepo.getFollowedAndNotInterested("Alpha", PageRequest.of(0, 10)).get();
		results.forEach(System.out::println);
		assertEquals(2, results.getNumberOfElements());
		assertNotNull(results.getContent().get(0).getBy());
		assertFalse(results.getContent().stream().filter(s -> "On Art".equals(s.getTitle())).findFirst().get().getAbout().isEmpty());

		results = sharedPerspectiveRepo.getFollowedAndNotInterested("Beta", PageRequest.of(0, 10)).get();
		assertEquals(1, results.getNumberOfElements());

		results = sharedPerspectiveRepo.getFollowedAndNotInterested("Gamma", PageRequest.of(0, 10)).get();
		assertEquals(1, results.getNumberOfElements());
	}

}
