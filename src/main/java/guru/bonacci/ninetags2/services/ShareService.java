package guru.bonacci.ninetags2.services;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.SharedWithRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import guru.bonacci.ninetags2.webdomain.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareService {

	private final SharedWithRepository swrepo;
	private final ShareRepository srepo;
	private final TopicRepository trepo;
	private final FakeSecurityContext context; 


	@Transactional
	public CompletableFuture<Long> insertPrivate(final Share.ShareBuilder shareBuilder, final List<Topic> topics) {
		_User user = context.getTheUser();
		
		val savedTopics = trepo.saveAll(topics);
		val share = shareBuilder.by(user).about(stream(savedTopics.spliterator(), false).collect(toList())).build();
		swrepo.save(SharedWith.builder().share(share).with(user).build());
		return CompletableFuture.completedFuture(srepo.save(share).getId());
	}


	@Transactional(readOnly = true)
	public CompletableFuture<PageDto<Share>> getReceivedDirectedShares(final Pageable pageRequest) {
		val directed = srepo.getReceivedDirectedShares(context.getAuthentication(), pageRequest);
		return directed.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found received directed share " + result)))
						.thenApply(results -> new PageDto<Share>(results.getContent()));
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<PageDto<Share>> getSentDirectedShares(final Pageable pageRequest) {
		val directed = srepo.getSentDirectedShares(context.getAuthentication(), pageRequest);
		return directed.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found sent directed share " + result)))
						.thenApply(results -> new PageDto<Share>(results.getContent()));
	}

}