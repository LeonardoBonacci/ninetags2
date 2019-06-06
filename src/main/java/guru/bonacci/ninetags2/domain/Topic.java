package guru.bonacci.ninetags2.domain;

import org.neo4j.ogm.annotation.Id;
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

	
	@Id
	String name;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Topic))
            return false;
 
        Topic other = (Topic) o;
 
        return name != null &&
               name.equals(other.getName());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
}
