package guru.bonacci.ninetags2.events;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static com.google.common.collect.Sets.*;

import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import guru.bonacci.ninetags2.domain.RelatesTo;
import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.repos.RelatesToRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShareEventHandler {

	private final RelatesToRepository relatesToRepo;
	
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreatedEvent(@NonNull CreationEvent<Share> creationEvent) {
		Share s = creationEvent.getSource();

		if (s.getAbout().size() < 2) return;
		
		Set<Set<Topic>> combinations = combinations(s.getAbout().stream().collect(toSet()), 2);
		combinations.stream().map(set -> set.stream().collect(toList())).forEach(combi -> {
			relatesToRepo.save(RelatesTo.builder().from(combi.get(0)).to(combi.get(1)).build());
		});
	}
}