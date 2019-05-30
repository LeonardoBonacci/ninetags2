package guru.bonacci.ninetags2.repos;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.RelatesTo;

@Repository
public interface RelatesToRepository extends Neo4jRepository<RelatesTo, Long> {

}