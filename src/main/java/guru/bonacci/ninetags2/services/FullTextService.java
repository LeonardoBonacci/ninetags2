package guru.bonacci.ninetags2.services;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.repos.FullTextSearchRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FullTextService {

	private final static String Q = "title:%1$s^10 OR description:%1$s^5 OR total:%1$s";

	private final FullTextSearchRepo ftRepo;

	
	@Transactional(readOnly = true)
	public CompletableFuture<List<Share>> search(@NonNull String searchQuery) {
		val shares = ftRepo.search(String.format(Q, searchQuery));
		return shares.whenComplete((results, ex) -> results.forEach(s -> log.info("ft share " + s)));
	}
}