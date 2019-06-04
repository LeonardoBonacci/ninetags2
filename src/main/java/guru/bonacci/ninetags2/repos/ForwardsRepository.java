package guru.bonacci.ninetags2.repos;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Forwards;

@Repository
public interface ForwardsRepository extends Neo4jRepository<Forwards, Long> {
}