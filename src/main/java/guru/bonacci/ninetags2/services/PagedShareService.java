package guru.bonacci.ninetags2.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import guru.bonacci.ninetags2.web.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagedShareService {

	private final ShareRepository repo;
	private final FakeSecurityContext context; 

	
	public CompletableFuture<PageDto<Share>> findAll(final Pageable pageable) {
//		CompletableFuture<List<Share>> top = repo.findAllBy(pageable);
//		CompletableFuture<List<Share>> middle = repo.findAllBy(pageable);
//		CompletableFuture<List<Share>> bottom = repo.findAllBy(pageable);
//
//		CompletionStage<PageDto<Share>> combinedPageResult = 
//				CompletableFuture.allOf(top, middle, bottom)
//								 .thenApply(ignoredVoid -> new PageDto<Share>(top.join(), middle.join(), bottom.join()));	
//
//		return combinedPageResult.toCompletableFuture();
		return null;
	}
	
	
	@Transactional(readOnly = true)
	public CompletableFuture<PageDto<Share>> getPrivateShares(final Pageable page) {
		CompletableFuture<Page<Share>> privates = repo.getPrivateShares(context.getAuthentication(), page);
		return privates.whenComplete((results, ex) -> results.stream().forEach(result -> log.info("found private share " + result)))
						.thenApply(results -> new PageDto<Share>(results.getContent()));
	}

}