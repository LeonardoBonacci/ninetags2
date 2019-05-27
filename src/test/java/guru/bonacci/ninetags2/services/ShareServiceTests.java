package guru.bonacci.ninetags2.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
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
	UserRepository urepo;

	@Autowired
	TopicRepository trepo;

	@Autowired
	ShareRepository srepo;

	@Autowired
	SharedWithRepository swrepo;

	_User user;
	

	@Before
	public void setUp() {
		urepo.deleteAll();
		trepo.deleteAll();
		srepo.deleteAll();
		swrepo.deleteAll();
	
		String username = "Alpha";
		securityContext.setAuthentication(username);
		user = urepo.save(_User.builder().name(username).build());
	}

	@Test
	public void testInsertPrivateShare() throws InterruptedException, ExecutionException {
		String title = "test share";
		Share.ShareBuilder share = Share.builder().title(title);
		
		Long shareId = sservice.insertPrivate(share, Arrays.asList(new Topic("test topic 1"), new Topic("test topic 2"))).get();

		Iterator<Topic> ts = trepo.findAll().iterator();
		ts.next(); ts.next();
		assertFalse(ts.hasNext());
		
		assertEquals(title, srepo.findById(shareId).get().getTitle());
		SharedWith sw = swrepo.findAll().iterator().next();
		assertEquals(title, sw.getShare().getTitle());
		assertEquals(user.getName(), sw.getWith().getName());
	}
}
