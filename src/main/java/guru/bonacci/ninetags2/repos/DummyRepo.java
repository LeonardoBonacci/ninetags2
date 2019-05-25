package guru.bonacci.ninetags2.repos;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;

@Repository
public class DummyRepo {

	public CompletableFuture<List<Share>> findAllBy(final Pageable pageable) {
		return CompletableFuture.supplyAsync(() -> Arrays.asList(new Share("aa")));
	}
}