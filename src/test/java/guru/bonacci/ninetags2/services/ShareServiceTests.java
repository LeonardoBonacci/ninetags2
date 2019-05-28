package guru.bonacci.ninetags2.services;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.SharedWithRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.repos.UserRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration("/test-context.xml")
public class ShareServiceTests {

	@Autowired
	ShareService sservice;

	@Autowired
	FakeSecurityContext securityContext;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	TopicRepository topicRepo;

	@Autowired
	ShareRepository shareRepo;

	@Autowired
	SharedWithRepository sharedWithRepo;

	_User sender;
	String username1 = "Beta", username2 = "Gamma";

	@Before
	public void setUp() {
		userRepo.deleteAll();
		topicRepo.deleteAll();
		shareRepo.deleteAll();
		sharedWithRepo.deleteAll();
	
		String username = "Alpha";
		securityContext.setAuthentication(username);
		sender = userRepo.save(_User.builder().name(username).build());

		userRepo.save(_User.builder().name(username1).build());
		userRepo.save(_User.builder().name(username2).build());
	}

	@Test
	public void testInsertPrivateShare() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title);
		
		Long shareId = sservice.insertPrivate(share, asList(new Topic("test topic 1"), new Topic("test topic 2"))).get();

		Iterator<Topic> ts = topicRepo.findAll().iterator();
		ts.next(); ts.next();
		assertFalse(ts.hasNext());
		
		assertEquals(title, shareRepo.findById(shareId).get().getTitle());
		Iterator<SharedWith> sws = sharedWithRepo.findAll().iterator();

		SharedWith sw = sws.next();
		assertEquals(title, sw.getShare().getTitle());
		assertEquals(sender.getName(), sw.getWith().getName());
		assertFalse(sws.hasNext());
	}
	
	@Test
	public void testInsertDirectedShare() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title);
		
		Long shareId = sservice.insertDirected(share, asList(new Topic("test topic 1"), new Topic("test topic 2")), asList(username1, username2)).get();

		Iterator<Topic> ts = topicRepo.findAll().iterator();
		ts.next(); ts.next();
		assertFalse(ts.hasNext());
		
		assertEquals(title, shareRepo.findById(shareId).get().getTitle());
		
		Iterator<SharedWith> sws = sharedWithRepo.findAll().iterator();
		SharedWith sw = sws.next();
		assertEquals(title, sw.getShare().getTitle());
		assertEquals(username1, sw.getWith().getName());
		
		sw = sws.next();
		assertEquals(title, sw.getShare().getTitle());
		assertEquals(username2, sw.getWith().getName());
		assertFalse(sws.hasNext());
	}

}
