package guru.bonacci.ninetags2.experimental;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervalRepository extends Neo4jRepository<Interval, Long> {
}