package guru.bonacci.ninetags2.repos;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;

@Repository
public interface ShareRepository extends Neo4jRepository<Share, Long> {


	CompletableFuture<Share> findByTitle(final String title);

	CompletableFuture<List<Share>> findByTitleContaining(String title);


	@Query(value = "MATCH (sharer:User {name:{name}})-[shared:SHARED]->(share:Share) " + 
					"WITH share, shared, sharer " + 
					"OPTIONAL MATCH (share)-[:SHARED_WITH]->(receiver:User) " +
					"WITH share, shared, sharer, receiver " +
					"WHERE receiver IS NULL " +
					"OPTIONAL MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
					"RETURN share, shared, sharer, about, topic " + 
					"ORDER BY ID(share) DESC ",
			countQuery = "MATCH (sharer:User {name:{name}})-[shared:SHARED]->(share:Share) " + 
					"WITH share, shared, sharer " + 
					"OPTIONAL MATCH (share)-[:SHARED_WITH]->(receiver:User) " +
					"WITH share, shared, sharer, receiver " +
					"WHERE receiver IS NULL " +
					"RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getSentShares(@Param("name") String name, Pageable pageRequest);

	@Query(value = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[shared:SHARED]-(sharer:User) " + 
					"WHERE user = sharer " + 
					"WITH share, shared, sharer " + 
					"OPTIONAL MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
					"RETURN share, shared, sharer, about, topic " + 
					"ORDER BY ID(share) DESC ",
		   countQuery = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[:SHARED]-(sharer:User) " + 
					"WHERE user = sharer " + 
					"RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getPrivateShares(@Param("name") String name, Pageable pageRequest);


	@Query(value = "MATCH (sharer:User {name:{name}})-[shared:SHARED]->(share:Share)-[:SHARED_WITH]->(receiver:User) " + 
					"WHERE sharer <> receiver " + 
					"WITH share, shared, sharer " + 
					"OPTIONAL MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
					"RETURN share, shared, sharer, about, topic " + 
					"ORDER BY ID(share) DESC ",
		   countQuery = "MATCH (user:User {name:{name}})-[:SHARED]->(share:Share)-[:SHARED_WITH]->(receiver:User) " + 
					"WHERE user <> receiver " + 
					"RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getSentDirectedShares(@Param("name") String name, Pageable pageRequest);

	
	@Query(value = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[shared:SHARED]-(sharer:User) " + 
					"WHERE user <> sharer " + 
					"WITH share, shared, sharer " + 
					"OPTIONAL MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
					"RETURN share, shared, sharer, about, topic " + 
					"ORDER BY ID(share) DESC ",
		   countQuery = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[:SHARED]-(sharer:User) " + 
				   "WHERE user <> sharer " + 
				   "RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getReceivedDirectedShares(@Param("name") String name, Pageable pageRequest);

}