package guru.bonacci.ninetags2.webdomain;

import javax.validation.constraints.NotBlank;

import guru.bonacci.ninetags2.validation.FirstChecks;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@EqualsAndHashCode(of = "username", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends PrioDto {


	@NotBlank(
            message = "No topic no...",
            groups = FirstChecks.class
    )
	private String username;
}
