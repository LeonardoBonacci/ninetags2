package guru.bonacci.ninetags2.webdomain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true) 
@NoArgsConstructor
@AllArgsConstructor
public class DirectedShareDto extends ShareDto {

	private List<String> users;
}
