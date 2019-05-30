package guru.bonacci.ninetags2.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

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
	
	@Index(unique = true) 
	@NotBlank
	String name;

	@Relationship(type = "FOLLOWS")
	@Builder.Default 
	Set<Follows> follows = new HashSet<>();

	@Relationship(type = "INTERESTED_IN")
	@Builder.Default 
	Set<Interests> interests = new HashSet<>();
	

	public void addFollows(@NonNull _User... followUs) {
		for (_User followMe : followUs)
			addFollows(followMe);
	}	

	private void addFollows(@NonNull _User followMe) {
		this.follows.add(Follows.builder().follower(this).followed(followMe).prio(this.follows.size()).build());
	}	

	public void deleteFollows(@NonNull _User... doNotfollowUs) {
		for (_User doNotFollowMe : doNotfollowUs)
			deleteFollows(doNotFollowMe);
	}	

	private void deleteFollows(@NonNull _User doNotfollowMe) {
		this.follows.removeIf(f -> f.getFollowed().getName().equals(doNotfollowMe.getName()));
	}	

	public void addInterests(@NonNull Topic... followUs) {
		for (Topic followMe : followUs)
			addInterest(followMe);
	}	

	private void addInterest(@NonNull Topic followMe) {
		this.interests.add(Interests.builder().follower(this).followed(followMe).prio(this.interests.size()).build());
	}	
	
	public void deleteInterests(@NonNull Topic... doNotfollowUs) {
		for (Topic doNotFollowMe : doNotfollowUs)
			deleteInterest(doNotFollowMe);
	}	

	private void deleteInterest(@NonNull Topic doNotfollowMe) {
		this.interests.removeIf(i -> i.getFollowed().getName().equals(doNotfollowMe.getName()));
	}	
}
