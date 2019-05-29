package guru.bonacci.ninetags2.repos;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Likes;
import guru.bonacci.ninetags2.domain.Share;

@Repository
public interface LikesRepository extends Neo4jRepository<Likes, Long> {

	
	@Query(value = "CALL ga.timetree.now({}) " + 
			"YIELD instant as today " +
			"MATCH (user:User {name:{name}})-[:FOLLOWS]->(followed:User)-[shared:SHARED]->(share:Share)-[:AT_TIME]->(today) " + 
			"WHERE user <> followed " +
			"WITH share, shared, followed " +
			"MATCH (share)<-[likes:LIKES]-(:User) " +
			"WITH DISTINCT(share) AS shares, COUNT(likes) AS nrLikes, shared, followed " +
//			"MATCH (share)-[about:IS_ABOUT]->(topic:Topic) " +
			"RETURN shares, shared, followed " +
			"ORDER BY nrLikes DESC ",
		   countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getMostLikedFromFollowed(@Param("name") String name, Pageable pageRequest);

	@Query(value = "CALL ga.timetree.now({}) " + 
			"YIELD instant as today " +
			"MATCH (user:User {name:{name}})-[:INTERESTED_IN]->(:Topic)<-[:IS_ABOUT]-(share:Share)-[:AT_TIME]->(today) " + 
			"WITH share, user " +
			"MATCH (share)<-[likes:LIKES]-(:User) " +
			"MATCH (share)<-[shared:SHARED]-(sharer:User) " +
			"WHERE user <> sharer " +
			"WITH DISTINCT(share) AS shares, COUNT(likes) AS nrLikes, shared, sharer " +
			"RETURN shares, shared, sharer " +
			"ORDER BY nrLikes DESC ",
		   countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Share>> getMostLikedFromInterests(@Param("name") String name, Pageable pageRequest);

}