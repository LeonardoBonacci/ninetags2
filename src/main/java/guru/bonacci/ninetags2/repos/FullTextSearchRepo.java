package guru.bonacci.ninetags2.repos;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;

@Repository
public interface FullTextSearchRepo extends Neo4jRepository<Share, Long> {


	@Query(value = "CALL db.index.fulltext.createNodeIndex('shares', ['Share'], ['title', 'description', 'crawl']) ")
	void init();

	
	@Query(value = "CALL db.index.fulltext.drop('shares') ")
	void clean();

	
	@Query(value = "CALL db.index.fulltext.queryNodes('shares', {q}) ")
	CompletableFuture<List<Share>> search(@Param("q") String q);
}