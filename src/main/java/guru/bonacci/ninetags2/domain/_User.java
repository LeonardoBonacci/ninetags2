package guru.bonacci.ninetags2.domain;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NodeEntity("User")
@NoArgsConstructor
@AllArgsConstructor
public class _User {

	@Id @GeneratedValue
	private Long id;
	
	@Index(unique=true) 
	private String name;

	@Relationship(type = "FOLLOWS")
	@Builder.Default 
	private List<_User> follows = new ArrayList<>();

	
	public void addFollows(_User... followUs) {
		follows.addAll(asList(followUs));
	}	
}
