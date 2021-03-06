package guru.bonacci.ninetags2.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PrioValidator.class)
@Documented
public @interface Prio {

    String message() default "prio must be an ascending (order is irrelevant) non-interupted sequence.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}