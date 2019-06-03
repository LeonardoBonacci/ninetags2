package guru.bonacci.ninetags2.webdomain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import guru.bonacci.ninetags2.validation.TopicChecks;
import guru.bonacci.ninetags2.validation.TopicListChecks;
import guru.bonacci.ninetags2.validation.TopicPrio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({ TopicChecks.class, TopicDtoList.class, TopicListChecks.class })
public class TopicDtoList {

	@Valid
	@NotEmpty(groups = TopicChecks.class)
    @Delegate
	@TopicPrio(groups = TopicListChecks.class)
    private List<TopicDto> topics = new ArrayList<>();
}