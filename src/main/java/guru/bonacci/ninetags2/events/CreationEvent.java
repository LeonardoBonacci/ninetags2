package guru.bonacci.ninetags2.events;

import org.springframework.context.ApplicationEvent;

public class CreationEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = -8053143381029977953L;


	public CreationEvent(final T source) {
        super(source);
    }

    public CreationEvent(final T source, final String message) {
        super(source);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public T getSource() {
    	return (T)super.getSource();
    }
}