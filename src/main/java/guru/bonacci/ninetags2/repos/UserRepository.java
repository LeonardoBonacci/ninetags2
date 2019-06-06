package guru.bonacci.ninetags2.repos;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain._User;

@Repository
public interface UserRepository extends Neo4jRepository<_User, String> {


	List<_User> findByNameIn(List<String> name);

	
	@Query( "MATCH (User {name:{name}})<-[:FOLLOWS]-(follower:User) " + 
			"RETURN follower")
	CompletableFuture<List<_User>> getFollowers(@Param("name") String name);
	
	
	@Query(value = "MATCH (user:User {name:{name}})-[follows:FOLLOWS]->(followed:User) " + 
					"WHERE user <> followed " + 
					"RETURN followed " + 
					"ORDER BY follows.prio ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<_User>> getFollowed(@Param("name") String name, Pageable pageRequest);


	@Query(value = "MATCH (:Topic {name:{name}})<-[:IS_ABOUT]-(:Share)<-[:SHARED]-(sharer:User) " + 
					"WITH sharer, COUNT(sharer) as nrSharings " +
					"RETURN sharer " + 
					"ORDER BY nrSharings DESC ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<_User>> getMostSharingOnTopic(@Param("name") String name, Pageable pageRequest);

	
	@Query(value = "CALL ga.timetree.now({}) " + 
				"YIELD instant as today " +
				"MATCH ()-[likes:LIKES]->(share:Share)-[:AT_TIME]->(today) " + 
				"MATCH (sharer:User)-[shared:SHARED]->(share) " + 
				"WITH sharer, COUNT(likes) AS nrLikes " +
				"RETURN sharer " +
				"ORDER BY nrLikes DESC ",
		   countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<_User>> getMostLikedUsersToday(Pageable pageRequest);
	
	
	@Query( "MATCH (user:User {name: {name}}) " + 
			"CALL algo.pageRank.stream('User', 'FOLLOWS', {iterations:20, dampingFactor:0.85, sourceNodes: [user]}) " +
			"YIELD nodeId, score " +
			"WITH algo.asNode(nodeId) AS u, score " +
			"WHERE NOT(EXISTS((user)-[:FOLLOWS]->(u))) AND u.name <> {name} " +
			"RETURN u " + 
			"ORDER BY score DESC ")
	CompletableFuture<List<_User>> recommendUsersToFollow(@Param("name") String name);
}