package guru.bonacci.ninetags2.web;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;

@Value
public class PageDto<T> {

	// A page is a 3 by 3 matrix
	List<T> top = new ArrayList<>(3);
	List<T> middle = new ArrayList<>(3);
	List<T> bottom = new ArrayList<>(3);

	
	public PageDto(List<T> top, List<T> middle, List<T> bottom) {
		this.top.addAll(top);
		this.middle.addAll(middle);
		this.bottom.addAll(bottom);
	}
}
