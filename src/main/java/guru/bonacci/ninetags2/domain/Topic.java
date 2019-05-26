package guru.bonacci.ninetags2.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

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
public class Topic {

	@Id @GeneratedValue
	private Long id;
	
	@Index(unique=true) 
	private String name;
}
