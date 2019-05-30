package guru.bonacci.ninetags2.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	
	@JsonIgnore
	@Id @GeneratedValue
	Long id;

	@Property @Index 
	Integer prio;

	@StartNode 
	_User follower;
	
	@EndNode 
	_User followed;
}
