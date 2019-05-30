package guru.bonacci.ninetags2.experimental;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

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
public class Location {

	
	@Id @GeneratedValue
	Long id;
	
	@Property
	String name;

	Float longitude;
	Float latitude;
}
