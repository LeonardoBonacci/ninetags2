package guru.bonacci.ninetags2.webdomain;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareDto {

	@NotBlank
	private String title;

    @URL
	public String url;

    @Size(min = 1, max = 9)
	private List<String> topics;
}
