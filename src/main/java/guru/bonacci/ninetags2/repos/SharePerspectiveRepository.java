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


	@Query(value = "MATCH (user:User {name:{name}})-[FOLLOWS]->(followed:User)-[shared:SHARED]->(share:Share) " + 
					"WHERE user <> followed " +
					"WITH user, share, shared, followed " + 
					"MATCH (share)-[IS_ABOUT]->(Topic)<-[INTERESTED_IN]-(user) " +
					"WITH share, shared, followed " +
					"MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
					"RETURN share, shared, followed, about, topic ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getFollowedAndInterested(@Param("name") String name, Pageable pageRequest);


	@Query(value = "MATCH (user:User {name:{name}})-[FOLLOWS]->(followed:User)-[shared:SHARED]->(share:Share) " + 
					"WHERE user <> followed " +
					"WITH user, share, shared, followed " + 
					"OPTIONAL MATCH (share)-[IS_ABOUT]->(Topic)<-[interest:INTERESTED_IN]-(user) " +
					"WITH share, shared, followed, interest " + 
					"WHERE interest IS NULL " +
					"MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
					"RETURN share, shared, followed, about, topic ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getFollowedAndNotInterested(@Param("name") String name, Pageable pageRequest);


	@Query(value = "MATCH (user:User {name:{name}})-[:INTERESTED_IN]->(Topic)<-[IS_ABOUT]-(share:Share) " + 
					"WITH user, share " +
					"MATCH (share)<-[shared:SHARED]-(followed:User)<-[FOLLOWS]-(user) " +
					"WHERE user <> followed " +
					"MATCH (topic:Topic)<-[about:IS_ABOUT]-(share) " +
					"RETURN share, shared, followed, about, topic ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getInterestedAndFollowed(@Param("name") String name, Pageable pageRequest);

	
	@Query(value = "MATCH (user:User {name:{name}})-[INTERESTED_IN]->(Topic)<-[IS_ABOUT]-(share:Share) " + 
					"WITH user, share " +
					"OPTIONAL MATCH (share)<-[SHARED]-(User)<-[follows:FOLLOWS]-(user) " +
					"WITH user, share, follows " +
					"WHERE follows IS NULL " + 
					"MATCH (share)<-[shared:SHARED]-(sharer:User) " +
					"WHERE user <> sharer " +
					"MATCH (topic:Topic)<-[about:IS_ABOUT]-(share) " +
					"RETURN share, shared, sharer, about, topic ",
		  countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getInterestedAndNotFollowed(@Param("name") String name, Pageable pageRequest);


	
	
	
	
	
	 

}