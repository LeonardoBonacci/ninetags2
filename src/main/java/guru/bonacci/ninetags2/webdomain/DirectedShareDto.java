package guru.bonacci.ninetags2.webdomain;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true) 
@NoArgsConstructor
@AllArgsConstructor
public class DirectedShareDto extends ShareDto {

	private List<@NotNull String> users;
}
