package guru.bonacci.ninetags2.webdomain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

	@Min(1)
	private Integer prio;
	
	@NotBlank
	private String topic;
}
