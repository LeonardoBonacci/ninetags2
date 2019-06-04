package guru.bonacci.ninetags2.domain;

import javax.validation.constraints.NotBlank;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	Long id;
	
	@Index(unique = true) 
	@NotBlank
	String name;
	

	public Topic(String name) {
		this.name = name;
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Topic))
            return false;
 
        Topic other = (Topic) o;
 
        return id != null &&
               id.equals(other.getId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
}
