package guru.bonacci.ninetags2.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.repos.DummyRepo;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import guru.bonacci.ninetags2.web.PageDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagedShareService {

	private final FakeSecurityContext security; 

	private final DummyRepo repo;

	
	public CompletableFuture<PageDto<Share>> findAll(final Pageable pageable) {
		CompletableFuture<List<Share>> top = repo.findAllBy(pageable);
		CompletableFuture<List<Share>> middle = repo.findAllBy(pageable);
		CompletableFuture<List<Share>> bottom = repo.findAllBy(pageable);

		CompletionStage<PageDto<Share>> combinedPageResult = 
				CompletableFuture.allOf(top, middle, bottom)
								 .thenApply(ignoredVoid -> new PageDto<Share>(top.join(), middle.join(), bottom.join()));	

		return combinedPageResult.toCompletableFuture();
	}

	public CompletableFuture<Optional<Share>> findOneById(final String id) {
		return null;//repo.findOneById(id).thenApply(Optional::ofNullable);
	}
}