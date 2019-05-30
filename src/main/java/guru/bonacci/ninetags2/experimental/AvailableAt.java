package guru.bonacci.ninetags2.experimental;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import guru.bonacci.ninetags2.domain.Share;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@RelationshipEntity(type = "AVAILABLE_AT")
@NoArgsConstructor
@AllArgsConstructor
public class AvailableAt {

	
	@Id @GeneratedValue
	Long id;

	@Property
	Integer radius;
	
	@StartNode 
	Share share;
	
	@EndNode 
	Location location;
}
