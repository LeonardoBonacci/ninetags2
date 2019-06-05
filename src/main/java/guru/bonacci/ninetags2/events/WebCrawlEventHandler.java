package guru.bonacci.ninetags2.events;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.repos.ShareRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebCrawlEventHandler {

	private final ShareRepository shareRepo;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleShareCreatedEvent(@NonNull CreationEvent<Share> creationEvent) {
		Share created = creationEvent.getSource();
		created.setTotal("A tradition is a ritual, belief or object that is passed down within a culture, still maintained in the present but with origins in the past. Many traditions and traditional stories have been passed down to us over the generations. Learn about some of them here");
		shareRepo.save(created);
	}
}