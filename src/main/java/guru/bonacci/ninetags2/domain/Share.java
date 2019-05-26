package guru.bonacci.ninetags2.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import io.swagger.annotations.ApiModelProperty;
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
public class Share {

	@Id @GeneratedValue
	private Long id;
	
    @ApiModelProperty(notes = "whatever it is named by the user")
	@Index(unique=true) 
	private String title;

    @ApiModelProperty(notes = "url pointing to the content")
	public String url;

	@Relationship(type = "SHARED", direction=Relationship.INCOMING)
	private _User by;
}
