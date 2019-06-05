package guru.bonacci.ninetags2.events;

import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.repos.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebCrawlEventHandler {

	private final ShareRepository shareRepo;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleShareCreatedEvent(@NonNull CreationEvent<Share> creationEvent) {
		Share created = creationEvent.getSource();
		created.setTotal(crawl(created.getUrl()));
		shareRepo.save(created);
	}
	
	
	public String crawl(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla");
			conn.setRequestMethod("GET");
			Document doc = Jsoup.parse(conn.getInputStream(), null, "not-needed");
			return doc.text();
		} catch (Throwable ignore) {
			log.warn("crawl error on " + url);
			return null;
		} 
	}
}