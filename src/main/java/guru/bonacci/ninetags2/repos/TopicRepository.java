package guru.bonacci.ninetags2.repos;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Topic;

@Repository
public interface TopicRepository extends Neo4jRepository<Topic, Long> {

	@Depth(value = 0)
	@Query(value = "MATCH (user:User {name:{name}})-[interest:INTERESTED_IN]->(t:Topic) " + 
					"WITH interest, t " + 
					"RETURN t " + 
					"ORDER BY interest.prio ",
		   countQuery = "MATCH (user:User {name:{name}})-[:INTERESTED_IN]->(t:Topic) " + 
					"RETURN COUNT(t) " )
	CompletableFuture<Page<Topic>> getFollowed(@Param("name") String name, Pageable pageRequest);

}