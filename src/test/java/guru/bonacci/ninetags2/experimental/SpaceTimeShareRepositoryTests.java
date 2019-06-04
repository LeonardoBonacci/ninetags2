package guru.bonacci.ninetags2.experimental;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static com.google.common.collect.Sets.newHashSet;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.repos.UserRepository;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SpaceTimeShareRepositoryTests {

	@Autowired
	private SpaceTimeRepository spaceTimeRepo;

	@Autowired
	private ShareRepository shareRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TopicRepository topicRepo;

	@Autowired
	private LocationRepository locRepo;

	@Autowired
	private IntervalRepository intRepo;

	@Autowired
	private AvailableAtRepository avRepo;

	
	@Before
	public void setUp() {
		shareRepo.deleteAll();
		userRepo.deleteAll();
		topicRepo.deleteAll();
		intRepo.deleteAll();
		locRepo.deleteAll();
		avRepo.deleteAll();
	

		val culture = Topic.builder().name("Culture").build();
		val logic = Topic.builder().name("Logic").build();
		topicRepo.saveAll(asList(culture, logic));

		
		val alpha = _User.builder().name("Alpha").build();
		val beta = _User.builder().name("Beta").build();
		val gamma = _User.builder().name("Gamma").build();
		userRepo.saveAll(asList(alpha, beta, gamma));

		
		val s1 = Share.builder().title("On Culture").by(alpha).about(newHashSet(culture)).time(System.currentTimeMillis()).build();
		val s2 = Share.builder().title("On Logic").by(alpha).about(newHashSet(logic)).time(System.currentTimeMillis()).build();
		val s3 = Share.builder().title("bbc").by(beta).about(newHashSet(culture)).time(System.currentTimeMillis()).build();
		val s4 = Share.builder().title("cult syn").by(beta).about(newHashSet(culture)).time(System.currentTimeMillis()).build();
		val s5 = Share.builder().title("beach cul").by(gamma).about(newHashSet(culture)).time(System.currentTimeMillis()).build();
		val s6 = Share.builder().title("cul europe").by(gamma).about(newHashSet(culture)).time(System.currentTimeMillis()).build();
		shareRepo.saveAll(asList(s1, s2, s3, s4, s5, s6));

		Location adam = Location.builder().name("Amsterdam").longitude(4.8936041f).latitude(52.3727598f).build();
		Location boek = Location.builder().name("Boekelo").longitude(6.7994207f).latitude(52.204006f).build();
		Location frio = Location.builder().name("Cabo Frio").longitude(-42.0189227f).latitude(-22.8804369f).build();
		Location del = Location.builder().name("Delft").longitude(4.35839f).latitude(52.0114017f).build();
		Location ensch = Location.builder().name("Enschede").longitude(6.8940537f).latitude(52.2209855f).build();
		locRepo.saveAll(asList(adam, boek, frio, del, ensch));
	
		AvailableAt av1 = AvailableAt.builder().share(s1).location(adam).radius(100).build();
		AvailableAt av2 = AvailableAt.builder().share(s2).location(boek).radius(500).build();
		AvailableAt av3 = AvailableAt.builder().share(s3).location(frio).radius(200).build();
		AvailableAt av4 = AvailableAt.builder().share(s4).location(del).radius(20).build();
		AvailableAt av5 = AvailableAt.builder().share(s5).location(adam).radius(50).build();
		AvailableAt av6 = AvailableAt.builder().share(s5).location(ensch).radius(75).build();
		avRepo.saveAll(asList(av1, av2, av3, av4, av5, av6));
		
		Interval i1 = Interval.builder().from(System.currentTimeMillis()).until(System.currentTimeMillis() + 1000000).share(s1).build();
		Interval i2 = Interval.builder().from(System.currentTimeMillis()).share(s2).build();
		Interval i3 = Interval.builder().from(System.currentTimeMillis()).share(s3).build();
		Interval i4 = Interval.builder().from(System.currentTimeMillis()).until(System.currentTimeMillis() + 1000000).share(s4).build();
		Interval i5 = Interval.builder().from(System.currentTimeMillis()).until(System.currentTimeMillis()).share(s5).build();
		Interval i6 = Interval.builder().from(System.currentTimeMillis()).until(System.currentTimeMillis() + 1000000).share(s6).build();
		intRepo.saveAll(asList(i1, i2, i3, i4, i5, i6));
	}

	@Test
	public void testSpaceTimeShares() throws InterruptedException, ExecutionException {
		assertEquals(2, spaceTimeRepo.getSpaceTimeShares().size());
	}

}
