package guru.bonacci.ninetags2.webdomain;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import guru.bonacci.ninetags2.validation.TopicChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({ TopicChecks.class, TopicDto.class })
public class TopicDto {

	@NotNull @Min(value = 1, groups = TopicChecks.class)
	private Integer prio;
	
	@NotBlank(groups = TopicChecks.class)
	private String topic;
}
