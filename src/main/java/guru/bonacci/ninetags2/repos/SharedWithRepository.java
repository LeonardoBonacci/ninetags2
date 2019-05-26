package guru.bonacci.ninetags2.repos;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.SharedWith;

@Repository
public interface SharedWithRepository extends Neo4jRepository<SharedWith, Long> {

}