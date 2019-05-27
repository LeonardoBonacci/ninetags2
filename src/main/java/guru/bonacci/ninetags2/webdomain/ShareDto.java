package guru.bonacci.ninetags2.webdomain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareDto {

	private String title;

	public String url;

	private List<String> topics;
}
