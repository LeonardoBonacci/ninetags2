package guru.bonacci.ninetags2.experimental;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableAtRepository extends Neo4jRepository<AvailableAt, Long> {
}