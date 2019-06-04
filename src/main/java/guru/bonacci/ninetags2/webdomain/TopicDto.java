package guru.bonacci.ninetags2.webdomain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import guru.bonacci.ninetags2.validation.FirstChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

	@NotNull(
			message = "Where is the prio?",
			groups = FirstChecks.class) 
	@Positive
	private Integer prio;
	
    @NotBlank(
            message = "No topic no...",
            groups = FirstChecks.class
    )
	private String topic;
}
