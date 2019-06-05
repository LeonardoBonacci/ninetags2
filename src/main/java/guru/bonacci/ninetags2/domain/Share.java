package guru.bonacci.ninetags2.domain;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder=true)
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Share {

	
	@Id @GeneratedValue
	Long id;
	
    @ApiModelProperty(notes = "whatever it is named by the user")
	@Index(unique = true) 
	String title;

    @ApiModelProperty(notes = "however it is described by the user")
	String description;

    @ApiModelProperty(notes = "total web text")
	String total;

    @JsonIgnore
	@CreatedDate
	Long time;

    @ApiModelProperty(notes = "url pointing to the content")
    String url;

	@Relationship(type = "SHARED", direction=Relationship.INCOMING)
	_User by;
	
	@Relationship(type = "IS_ABOUT")
	@Builder.Default 
	Set<Topic> about = newHashSet();
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Share))
            return false;
 
        Share other = (Share) o;
 
        return id != null &&
               id.equals(other.getId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
}
