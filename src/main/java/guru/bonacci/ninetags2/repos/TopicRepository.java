package guru.bonacci.ninetags2.repos;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Topic;

@Repository
public interface TopicRepository extends Neo4jRepository<Topic, String> {


	CompletableFuture<List<Topic>> findByNameContaining(String name);

	
	@Query(value = "MATCH (user:User {name:{name}})-[interest:INTERESTED_IN]->(t:Topic) " + 
					"WITH interest, t " + 
					"RETURN t " + 
					"ORDER BY interest.prio ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Topic>> getFollowed(@Param("name") String name, Pageable pageRequest);

	
	@Query(value = "MATCH (:User {name:{name}})-[:SHARED]->(:Share)-[:IS_ABOUT]->(topic:Topic) " + 
					"WITH topic, COUNT(topic) AS nrSharings " +
					"RETURN topic " + 
					"ORDER BY nrSharings DESC ",
			countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Topic>> getMostSharingOnUser(@Param("name") String name, Pageable pageRequest);

	
	@Query(value = "CALL ga.timetree.now({}) " + 
			"YIELD instant as today " +
			"MATCH ()-[likes:LIKES]->(share:Share)-[:AT_TIME]->(today) " + 
			"MATCH (topic:Topic)<-[:IS_ABOUT]-(share) " +
			"WITH topic, COUNT(likes) AS nrLikes " +
			"RETURN topic " +
			"ORDER BY nrLikes DESC ",
		   countQuery = "RETURN COUNT(0) " )
	CompletableFuture<Page<Topic>> getMostLikedTopicsToday(Pageable pageRequest);
	

	// this was to be done with cypher projection if escaping special characters would not be such a pain...
	@Query( "CALL algo.closeness.harmonic.stream('Topic', 'RELATES_TO') " + 
			"YIELD nodeId, centrality " +
			"WITH algo.asNode(nodeId) AS t, centrality " +
			"MATCH (:User {name:{name}})-[:INTERESTED_IN]->(already:Topic)-[:RELATES_TO*1..4]-(t) " +
			"WHERE already <> t " +
			"RETURN t " + 
			"ORDER BY centrality DESC ")
	CompletableFuture<List<Topic>> recommendTopicsToFollow(@Param("name") String name);
}