package guru.bonacci.ninetags2.domain;

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
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@ToString
@NodeEntity("User")
@NoArgsConstructor
@AllArgsConstructor
public class _User {

	
	@Id @GeneratedValue
	Long id;
	
	@Index(unique=true) 
	String name;

	@Relationship(type = "FOLLOWS")
	@Builder.Default 
	List<Follows> follows = new ArrayList<>();

	@Relationship(type = "INTERESTED_IN")
	@Builder.Default 
	List<Interests> interests = new ArrayList<>();
	

	public void addFollows(@NonNull _User... followUs) {
		for (_User followMe : followUs)
			addFollows(followMe);
	}	

	private void addFollows(@NonNull _User followMe) {
		this.follows.add(Follows.builder().follower(this).followed(followMe).prio(this.follows.size()).build());
	}	

	public void addInterests(@NonNull Topic... followUs) {
		for (Topic followMe : followUs)
			addInterest(followMe);
	}	

	private void addInterest(@NonNull Topic followMe) {
		this.interests.add(Interests.builder().follower(this).followed(followMe).prio(this.interests.size()).build());
	}	
}
