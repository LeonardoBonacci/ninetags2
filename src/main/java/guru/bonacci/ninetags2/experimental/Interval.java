package guru.bonacci.ninetags2.experimental;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import guru.bonacci.ninetags2.domain.Share;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Interval {

	
	@Id @GeneratedValue
	Long id;
	
	@Property
	Long from;
	
	@Index
	Long until;
	
	@Relationship(type = "AVAILABLE_DURING", direction=Relationship.INCOMING)
	Share share;
}
