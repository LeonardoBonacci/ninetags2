package guru.bonacci.ninetags2.webdomain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import guru.bonacci.ninetags2.validation.FirstChecks;
import guru.bonacci.ninetags2.validation.LastChecks;
import guru.bonacci.ninetags2.validation.TopicPrio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDtoList {


	@NotEmpty(message = "No topics to order", groups = FirstChecks.class)
	@UniqueElements(message = "Duplicate found!", groups = FirstChecks.class)
    @Delegate
    @TopicPrio(groups = LastChecks.class)
	private List<@Valid TopicDto> topics = new ArrayList<>();
}