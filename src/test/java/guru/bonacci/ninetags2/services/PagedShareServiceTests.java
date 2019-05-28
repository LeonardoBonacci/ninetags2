package guru.bonacci.ninetags2.services;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.UserRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration("/test-context.xml")
public class PagedShareServiceTests {

	@Autowired
	ShareService shareService;

	@Autowired
	PagedShareService pagedShareService;

	@Autowired
	FakeSecurityContext securityContext;
	
	@Autowired
	UserRepository userRepo;

	_User sender;
	String username1 = "Beta", username2 = "Gamma";

	
	@Before
	public void setUp() {
		userRepo.deleteAll();
	
		String username = "Alpha";
		securityContext.setAuthentication(username);
		sender = userRepo.save(_User.builder().name(username).build());

		userRepo.save(_User.builder().name(username1).build());
		userRepo.save(_User.builder().name(username2).build());
	}

	
	// TODO test complete json with by and topics
	@Test
	public void testGetPrivateShares() throws InterruptedException, ExecutionException {
		String title = "test share";
		
		shareService.insertPrivate(Share.builder().title(title), asList(new Topic("test topic 1"), new Topic("test topic 2"))).get();

		List<Share> shares = pagedShareService.getPrivateShares(PageRequest.of(0, 10)).get().getContent();
		assertEquals(1, shares.size());
		
		Share share = shares.get(0);
//		assertEquals(2, share.getAbout().size());
		assertNotNull(share.getBy());
		
	}
	
//	@Test
//	public void testGetReceivedDirectedShares() throws InterruptedException, ExecutionException {
//		String title = "test share";
//		Share.ShareBuilder share = Share.builder().title(title);
//		
//		Long shareId = shareService.insertDirected(share, asList(new Topic("test topic 1"), new Topic("test topic 2")), asList(username1, username2)).get();
//
//		Iterator<Topic> ts = topicRepo.findAll().iterator();
//		ts.next(); ts.next();
//		assertFalse(ts.hasNext());
//		
//		assertEquals(title, shareRepo.findById(shareId).get().getTitle());
//		
//		Iterator<SharedWith> sws = sharedWithRepo.findAll().iterator();
//		SharedWith sw = sws.next();
//		assertEquals(title, sw.getShare().getTitle());
//		assertEquals(username1, sw.getWith().getName());
//		
//		sw = sws.next();
//		assertEquals(title, sw.getShare().getTitle());
//		assertEquals(username2, sw.getWith().getName());
//		assertFalse(sws.hasNext());
//	}
//	
//	@Test
//	public void testGetSentDirectedShares() throws InterruptedException, ExecutionException {
//		String title = "test share";
//		Share.ShareBuilder share = Share.builder().title(title);
//		
//		Long shareId = shareService.insertDirected(share, asList(new Topic("test topic 1"), new Topic("test topic 2")), asList(username1, username2)).get();
//
//		Iterator<Topic> ts = topicRepo.findAll().iterator();
//		ts.next(); ts.next();
//		assertFalse(ts.hasNext());
//		
//		assertEquals(title, shareRepo.findById(shareId).get().getTitle());
//		
//		Iterator<SharedWith> sws = sharedWithRepo.findAll().iterator();
//		SharedWith sw = sws.next();
//		assertEquals(title, sw.getShare().getTitle());
//		assertEquals(username1, sw.getWith().getName());
//		
//		sw = sws.next();
//		assertEquals(title, sw.getShare().getTitle());
//		assertEquals(username2, sw.getWith().getName());
//		assertFalse(sws.hasNext());
//	}
}
