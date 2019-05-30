package guru.bonacci.ninetags2.experimental;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;

@Repository
public interface SpaceTimeRepository extends Neo4jRepository<Share, Long> {

	
	
	@Query(value = "MATCH (share:Share)-[availableAt:AVAILABLE_AT]->(shareLoc:Location), (share)-[:AVAILABLE_DURING]->(sharedTime:Interval) " + 
				"WITH share, point({ longitude: 5.1859604, latitude: 52.037247 }) AS here, point({ longitude: shareLoc.longitude, latitude: shareLoc.latitude }) AS there " + 
				"WHERE toInteger(distance(here, there)/1000) < availableAt.radius " + 
				"AND (sharedTime.from IS NULL OR timestamp() > sharedTime.from) AND (sharedTime.until IS NULL OR timestamp() < sharedTime.until) " +
				"RETURN share ",
			countQuery = "RETURN COUNT(0) " )
	List<Share> getSpaceTimeShares();
}