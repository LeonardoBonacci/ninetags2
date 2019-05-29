package guru.bonacci.ninetags2.services;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
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
public class ShareServicesTests {

	@Autowired
	ShareService shareService;

	@Autowired
	PagedShareService pagedShareService;

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
	public void testInsertAndRetrieveShare() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title);
		
		Long shareId = shareService.insert(share, asList(new Topic("test topic 1"), new Topic("test topic 2"))).get();

		Iterator<Topic> ts = topicRepo.findAll().iterator();
		ts.next(); ts.next();
		assertFalse(ts.hasNext());
		
		assertEquals(title, shareRepo.findById(shareId).get().getTitle());

		Share result = pagedShareService.retrieveSentShares(PageRequest.of(0, 1)).get().getContent().get(0);
		assertNotNull(result.getBy());
		assertFalse(result.getAbout().isEmpty());
	}

	@Test
	public void testCRUD() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title).url("foo");

		// create
		Long shareId = shareService.insert(share, asList(new Topic("test topic 1"), new Topic("test topic 2"))).get();

		// retrieve
		Share inserted = shareRepo.findById(shareId).get();
		assertEquals(title, inserted.getTitle());
		assertEquals("foo", inserted.getUrl());
		assertEquals(2, inserted.getAbout().size());

		// update
		share.url("bar").id(shareId);
		shareService.update(share, asList(new Topic("test topic 3"))).get();
		Share updated = shareRepo.findById(shareId).get();
		assertEquals(title, updated.getTitle());
		assertEquals("bar", updated.getUrl());
		assertEquals(1, updated.getAbout().size());
		
		// delete
		shareService.delete(shareId).get();
		assertFalse(shareRepo.findById(shareId).isPresent());
	}
	
	@Test
	public void testInsertAndRetrievePrivateShare() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title);
		
		Long shareId = shareService.insertPrivate(share, asList(new Topic("test topic 1"), new Topic("test topic 2"))).get();

		Iterator<Topic> ts = topicRepo.findAll().iterator();
		ts.next(); ts.next();
		assertFalse(ts.hasNext());
		
		assertEquals(title, shareRepo.findById(shareId).get().getTitle());
		Iterator<SharedWith> sws = sharedWithRepo.findAll().iterator();

		SharedWith sw = sws.next();
		assertEquals(title, sw.getShare().getTitle());
		assertEquals(sender.getName(), sw.getWith().getName());
		assertFalse(sws.hasNext());
		
		Share result = pagedShareService.retrievePrivateShares(PageRequest.of(0, 1)).get().getContent().get(0);
		assertNotNull(result.getBy());
		assertFalse(result.getAbout().isEmpty());
	}
	
	@Test
	public void testInsertAndRetrieveDirectedShare() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title);
		
		Long shareId = shareService.insertDirected(share, asList(new Topic("test topic 1"), new Topic("test topic 2")), asList(username1, username2)).get();

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
		
		Share result = pagedShareService.retrieveSentDirectedShares(PageRequest.of(0, 1)).get().getContent().get(0);
		assertNotNull(result.getBy());
		assertFalse(result.getAbout().isEmpty());

		// change to receiving user
		securityContext.setAuthentication(username1);
		result = pagedShareService.retrieveReceivedDirectedShares(PageRequest.of(0, 1)).get().getContent().get(0);
		assertNotNull(result.getBy());
		assertFalse(result.getAbout().isEmpty());
	}
}
