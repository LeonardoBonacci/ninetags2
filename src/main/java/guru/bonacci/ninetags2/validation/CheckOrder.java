package guru.bonacci.ninetags2.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({ FirstChecks.class, LastChecks.class, Default.class  })
public interface CheckOrder {
}