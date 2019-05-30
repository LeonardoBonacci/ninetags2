package guru.bonacci.ninetags2.domain;

import javax.validation.constraints.NotBlank;

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
	Long id;
	
	@Index(unique = true) 
	@NotBlank
	String name;
	

	public Topic(String name) {
		this.name = name;
	}
}
