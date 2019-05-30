package guru.bonacci.ninetags2.webdomain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import guru.bonacci.ninetags2.validation.TopicPrio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDtoList {

	@Valid
    @Delegate
	@NotEmpty
	@TopicPrio
    private List<TopicDto> topics = new ArrayList<>();
}