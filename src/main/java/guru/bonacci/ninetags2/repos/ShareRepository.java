package guru.bonacci.ninetags2.repos;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;

@Repository
public interface ShareRepository extends Neo4jRepository<Share, Long> {

	
	@Depth(value = 0)
	@Query(value = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[:SHARED]-(sharer:User) " + 
					"WHERE user = sharer " + 
					"RETURN share " + 
					"ORDER BY ID(share) DESC ",
		   countQuery = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[:SHARED]-(sharer:User) " + 
					"WHERE user = sharer " + 
					"RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getPrivateShares(@Param("name") String name, Pageable pageRequest);


	@Depth(value = 0)
	@Query(value = "MATCH (user:User {name:{name}})-[:SHARED]->(share:Share)-[:SHARED_WITH]->(receiver:User) " + 
					"WHERE user <> receiver " + 
					"RETURN share " + 
					"ORDER BY ID(share) DESC ",
		   countQuery = "MATCH (user:User {name:{name}})-[:SHARED]->(share:Share)-[:SHARED_WITH]->(receiver:User) " + 
					"WHERE user <> receiver " + 
					"RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getSentDirectShares(@Param("name") String name, Pageable pageRequest);

	
	@Depth(value = 0)
	@Query(value = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[:SHARED]-(sharer:User) " + 
					"WHERE user <> sharer " + 
					"RETURN share " + 
					"ORDER BY ID(share) DESC ",
		   countQuery = "MATCH (user:User {name:{name}})<-[:SHARED_WITH]-(share:Share)<-[:SHARED]-(sharer:User) " + 
				   "WHERE user <> sharer " + 
				   "RETURN COUNT(share) " )
	CompletableFuture<Page<Share>> getReceivedDirectShares(@Param("name") String name, Pageable pageRequest);

}