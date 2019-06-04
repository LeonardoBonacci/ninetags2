package guru.bonacci.ninetags2.domain;

import static java.util.Arrays.*;
import static java.util.function.Function.*;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

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
import lombok.val;

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
	Set<Follows> follows = newHashSet();

	@Relationship(type = "INTERESTED_IN")
	@Builder.Default 
	Set<Interests> interests = newHashSet();
	
	
	public _User(String username) {
		this.name = username;
	}

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof _User))
            return false;
 
        _User other = (_User) o;
 
        return id != null &&
               id.equals(other.getId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
    

    public void addFollows(@NonNull Follows... followUs) {
		val mapByUserName = follows.stream().collect(toMap(f -> f.getFollowed().getName(), identity()));
		stream(followUs).forEach(f -> mapByUserName.put(f.getFollowed().getName(), f));
		follows = mapByUserName.values().stream().collect(toSet());
	}	

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

	public void addInterests(@NonNull Interests... followUs) {
		val mapByTopicName = interests.stream().collect(toMap(i -> i.getFollowed().getName(), identity()));
		stream(followUs).forEach(f -> mapByTopicName.put(f.getFollowed().getName(), f));
		interests = mapByTopicName.values().stream().collect(toSet());
	}	

	public void addTopics(@NonNull Topic... followUs) {
		stream(followUs).forEach(this::addTopic);
	}	

	private void addTopic(@NonNull Topic followMe) {
		this.interests.add(Interests.builder().follower(this).followed(followMe).prio(this.interests.size()).build());
	}	
	
	public void deleteTopics(@NonNull Topic... doNotfollowUs) {
		stream(doNotfollowUs).forEach(this::deleteTopic);
	}	

	private void deleteTopic(@NonNull Topic doNotfollowMe) {
		this.interests.removeIf(i -> i.getFollowed().getName().equals(doNotfollowMe.getName()));
	}	
}
