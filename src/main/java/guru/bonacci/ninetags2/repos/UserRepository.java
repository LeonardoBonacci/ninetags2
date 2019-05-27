package guru.bonacci.ninetags2.repos;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain._User;

@Repository
public interface UserRepository extends Neo4jRepository<_User, Long> {

	@Depth(value = 0)
	CompletableFuture<Optional<_User>> findByName(String name);


	@Depth(value = 0)
	@Query( "MATCH (User {name:{name}})<-[:FOLLOWS]-(follower:User) " + 
			"RETURN follower")
	CompletableFuture<List<_User>> getFollowers(@Param("name") String name);
	
	
	@Depth(value = 0)
	@Query(value = "MATCH (user:User {name:{name}})-[follows:FOLLOWS]->(followed:User) " + 
					"WHERE user <> followed " + 
					"RETURN followed " + 
					"ORDER BY follows.prio ",
		   countQuery = "MATCH (user:User {name:{name}})-[follows:FOLLOWS]->(followed:User) " + 
					"WHERE user <> followed " + 
					"RETURN COUNT(followed) " )
	CompletableFuture<Page<_User>> getFollowed(@Param("name") String name, Pageable pageRequest);

	
}