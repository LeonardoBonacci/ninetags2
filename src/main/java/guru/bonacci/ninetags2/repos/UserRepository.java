package guru.bonacci.ninetags2.repos;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain._User;

@Repository
public interface UserRepository extends Neo4jRepository<_User, Long> {

	@Depth(value = 0)
	CompletableFuture<_User> findByName(String name);

	@Depth(value = 0)
	@Query( "MATCH (User {name:{name}})-[:FOLLOWS]->(followed:User) " + 
			"RETURN followed")
	CompletableFuture<List<_User>> findFollowers(@Param("name") String name);
}