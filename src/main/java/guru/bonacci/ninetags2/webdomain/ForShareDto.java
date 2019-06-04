package guru.bonacci.ninetags2.webdomain;

import java.util.List;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForShareDto {

    @Size(max = 8)
	private List<String> newtopics;
}
