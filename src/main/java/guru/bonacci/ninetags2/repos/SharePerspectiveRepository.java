package guru.bonacci.ninetags2.repos;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;

@Repository
public interface SharePerspectiveRepository extends Neo4jRepository<Share, Long> {


	@Query(value = "MATCH (user:User {name:{name}})-[:FOLLOWS]->(followed:User)-[shared:SHARED]->(share:Share) " + 
			"WHERE user <> followed " +
			"WITH user, share, shared, followed " + 
			"OPTIONAL MATCH (share)-[about:IS_ABOUT]->(topic:Topic)<-[interest:INTERESTED_IN]-(user) " +
			"WITH share, shared, followed, about, interest, topic " + 
			"WHERE interest IS NOT NULL " +
			"RETURN share, shared, followed, about, topic ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getFollowedAndInterested(@Param("name") String name, Pageable pageRequest);


	@Query(value = "MATCH (user:User {name:{name}})-[:FOLLOWS]->(followed:User)-[shared:SHARED]->(share:Share) " + 
					"WHERE user <> followed " +
					"WITH user, share, shared, followed " + 
					"OPTIONAL MATCH (share)-[about:IS_ABOUT]->(topic:Topic)<-[interest:INTERESTED_IN]-(user) " +
					"WITH share, shared, followed, about, interest, topic " + 
					"WHERE interest IS NULL " +
					"RETURN share, shared, followed, about, topic ",
	countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getFollowedAndNotInterested(@Param("name") String name, Pageable pageRequest);
}