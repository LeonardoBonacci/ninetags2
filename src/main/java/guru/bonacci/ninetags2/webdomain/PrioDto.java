package guru.bonacci.ninetags2.webdomain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import guru.bonacci.ninetags2.validation.FirstChecks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrioDto {

	@NotNull(
			message = "Where is the prio?",
			groups = FirstChecks.class) 
	@Positive
	private Integer prio;
}
