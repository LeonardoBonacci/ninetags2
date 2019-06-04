package guru.bonacci.ninetags2.validation;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import guru.bonacci.ninetags2.webdomain.PrioDto;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class PrioValidator implements ConstraintValidator<Prio, List<? extends PrioDto>> {


	@Override
	public boolean isValid(List<? extends PrioDto> topics, ConstraintValidatorContext context) {
		val sorted = topics.stream().sorted(comparing(PrioDto::getPrio)).collect(toList());
		val prioShouldBe = new StringBuilder();
		val prioIs = new StringBuilder();
		for (int i = 0, correctPrio = sorted.get(0).getPrio(); i < sorted.size(); i++, correctPrio++) {
			prioShouldBe.append(correctPrio);
			prioIs.append(sorted.get(i).getPrio());
		}

		return prioShouldBe.toString().equals(prioIs.toString());
	}
}