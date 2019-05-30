package guru.bonacci.ninetags2.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepo;
	private final FakeSecurityContext context;
	

	@Transactional(readOnly = true)
	public CompletableFuture<Page<Topic>> getFollowed(final Pageable pageRequest) {
		val followers = topicRepo.getFollowed(context.getAuthentication(), pageRequest);
		return followers.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found followed topic " + result)));
	}
}
