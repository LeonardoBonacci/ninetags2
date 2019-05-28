package guru.bonacci.ninetags2.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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
	@Index(unique=true) 
	String title;

    @ApiModelProperty(notes = "url pointing to the content")
	String url;

	@Relationship(type = "SHARED", direction=Relationship.INCOMING)
	_User by;
	
	@Relationship(type = "IS_ABOUT")
	@Builder.Default 
	List<Topic> about = new ArrayList<>();
}
