package guru.bonacci.ninetags2.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.repos.LikesRepository;
import guru.bonacci.ninetags2.repos.SharePerspectiveRepository;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import guru.bonacci.ninetags2.webdomain.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagedShareService {

	private final ShareRepository shareRepo;
	private final SharePerspectiveRepository sharedPerspectiveRepo;
	private final LikesRepository likesRepo;
	private final FakeSecurityContext context; 

	
	@Transactional(readOnly = true)
	public CompletableFuture<PageDto<Share>> retrieveUserPerspective(Pageable pageRequest) {
		String username = context.getAuthentication();

		PageRequest pr = PageRequest.of(pageRequest.getPageNumber(), 3);
		CompletableFuture<Page<Share>> top = sharedPerspectiveRepo.getFollowedAndInterested(username, pr);
		CompletableFuture<Page<Share>> middle = sharedPerspectiveRepo.getFollowedAndNotInterested(username, pr);
		CompletableFuture<Page<Share>> bottom = likesRepo.getMostLikedFromFollowed(username, pr); 
		
		CompletionStage<PageDto<Share>> combinedPageResult = 
		CompletableFuture.allOf(top, middle, bottom)
						 .thenApply(ignoredVoid -> new PageDto<Share>(top.join(), middle.join(), bottom.join()));	

		return combinedPageResult.toCompletableFuture();
	}


	@Transactional(readOnly = true)
	public CompletableFuture<PageDto<Share>> retrieveTopicPerspective(Pageable pageRequest) {
		String username = context.getAuthentication();

		PageRequest pr = PageRequest.of(pageRequest.getPageNumber(), 3);
		CompletableFuture<Page<Share>> top = sharedPerspectiveRepo.getInterestedAndFollowed(username, pr);
		CompletableFuture<Page<Share>> middle = sharedPerspectiveRepo.getInterestedAndNotFollowed(username, pr);
		CompletableFuture<Page<Share>> bottom = likesRepo.getMostLikedFromInterests(username, pr); 

		CompletionStage<PageDto<Share>> combinedPageResult = 
		CompletableFuture.allOf(top, middle, bottom)
						 .thenApply(ignoredVoid -> new PageDto<Share>(top.join(), middle.join(), bottom.join()));	

		return combinedPageResult.toCompletableFuture();
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<Share>> retrieveSentShares(final Pageable pageRequest) {
		val sents = shareRepo.getSentShares(context.getAuthentication(), pageRequest);
		return sents.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found public share " + result)));
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<Share>> retrievePrivateShares(final Pageable pageRequest) {
		val privates = shareRepo.getPrivateShares(context.getAuthentication(), pageRequest);
		return privates.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found private share " + result)));
	}


	@Transactional(readOnly = true)
	public CompletableFuture<Page<Share>> retrieveReceivedDirectedShares(final Pageable pageRequest) {
		val directed = shareRepo.getReceivedDirectedShares(context.getAuthentication(), pageRequest);
		return directed.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found received directed share " + result)));
	}

	
	@Transactional(readOnly = true)
	public CompletableFuture<Page<Share>> retrieveSentDirectedShares(final Pageable pageRequest) {
		val directed = shareRepo.getSentDirectedShares(context.getAuthentication(), pageRequest);
		return directed.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found sent directed share " + result)));
	}

}