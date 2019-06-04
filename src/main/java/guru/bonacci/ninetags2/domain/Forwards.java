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
@RelationshipEntity(type = "FORWARDS")
@NoArgsConstructor
@AllArgsConstructor
public class Forwards {

	
	@JsonIgnore
	@Id @GeneratedValue
	Long id;

	@StartNode 
	Share forShare;
	
	@EndNode 
	Share share;
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Forwards))
            return false;
 
        Forwards other = (Forwards) o;
 
        return id != null &&
               id.equals(other.getId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }

}
