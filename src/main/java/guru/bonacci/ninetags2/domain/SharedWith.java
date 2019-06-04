package guru.bonacci.ninetags2.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@RelationshipEntity(type = "SHARED_WITH")
@NoArgsConstructor
@AllArgsConstructor
public class SharedWith {

	
	@JsonIgnore
	@Id @GeneratedValue
	Long id;

	@StartNode 
	Share share;
	
	@EndNode 
	_User with;
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof SharedWith))
            return false;
 
        SharedWith other = (SharedWith) o;
 
        return id != null &&
               id.equals(other.getId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
}
