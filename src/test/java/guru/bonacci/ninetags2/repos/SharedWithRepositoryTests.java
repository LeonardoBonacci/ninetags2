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

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain._User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SharedWithRepositoryTests {

	@Autowired
	private SharedWithRepository swrepo;

	@Autowired
	private ShareRepository srepo;

	@Autowired
	private UserRepository repo;


	@Before
	public void setUp() {
		repo.deleteAll();
	
		_User alpha = _User.builder().name("Alpha").build();
		repo.save(alpha);
		_User beta = _User.builder().name("Beta").build();
		
		Share sculture = Share.builder().title("On Culture").by(alpha).build();
		Share scooking = Share.builder().title("On Cooking").by(alpha).build();
		Share shobbies = Share.builder().title("On Hobbies").by(alpha).build();
		Share sliterature = Share.builder().title("On Literature").by(alpha).build();
		Share sart = Share.builder().title("On Art").by(alpha).build();
		Share sentertainment = Share.builder().title("On Entertainment").by(alpha).build();
		Share sfiction = Share.builder().title("On Fiction").by(alpha).build();
		Share sgame = Share.builder().title("On Game").by(alpha).build();
		Share spoetry = Share.builder().title("On Poetry").by(alpha).build();
		Share ssports = Share.builder().title("On Sports").by(alpha).build();
		Share sdance = Share.builder().title("On Dance").by(alpha).build();
		srepo.saveAll(Arrays.asList(sculture, scooking, shobbies, sliterature, sart, sentertainment, sfiction, sgame, spoetry, ssports, sdance));
		repo.save(alpha);
		
		SharedWith swculture = SharedWith.builder().share(sculture).with(alpha).build();
		SharedWith swcooking = SharedWith.builder().share(scooking).with(alpha).build();
		SharedWith swhobbies = SharedWith.builder().share(shobbies).with(alpha).build();
		SharedWith swliterature = SharedWith.builder().share(sliterature).with(beta).build();

		swrepo.saveAll(Arrays.asList(swculture, swcooking, swhobbies, swliterature));
	}

	@Test
	public void testGetFollowedPaged() throws InterruptedException, ExecutionException {
		String name = "Alpha";
		Page<Share> results = srepo.getPrivateShares(name, PageRequest.of(0, 2)).get();
		assertEquals(2, results.getNumberOfElements());
		assertEquals(3, results.getTotalElements());
		
		results = srepo.getPrivateShares(name, PageRequest.of(1, 2)).get();
		assertEquals(1, results.getNumberOfElements());

		results = srepo.getPrivateShares(name, PageRequest.of(2, 2)).get();
		assertEquals(0, results.getNumberOfElements());
		
	}

}
