package guru.bonacci.ninetags2.webdomain;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.Value;

@Value
public class PageDto<T> {

	// A page is a 3 by 3 matrix
	List<T> top = new ArrayList<>(3);
	List<T> middle = new ArrayList<>(3);
	List<T> bottom = new ArrayList<>(3);


	public PageDto(@NonNull List<T> complete) {
		for (int i=0; i<complete.size(); i++) {
			T t = complete.get(i);
			if (i<3) top.add(t);
			else if (i<6) middle.add(t);
			else if (i<9) bottom.add(t);
		}
	}

	public PageDto(List<T> top, List<T> middle, List<T> bottom) {
		this.top.addAll(top);
		this.middle.addAll(middle);
		this.bottom.addAll(bottom);
	}
}
