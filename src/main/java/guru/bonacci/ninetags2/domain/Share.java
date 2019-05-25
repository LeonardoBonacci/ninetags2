package guru.bonacci.ninetags2.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Share {

    @ApiModelProperty(notes = "url pointing to the content")
	public String url;
}
