package guru.bonacci.ninetags2.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@RelationshipEntity(type = "FOLLOWS")
@NoArgsConstructor
@AllArgsConstructor
public class Follows {

	@Id @GeneratedValue
	private Long id;

	@Property @Index 
	private Integer prio;

	@StartNode 
	private _User  follower;
	
	@EndNode 
	private _User followed;
}
