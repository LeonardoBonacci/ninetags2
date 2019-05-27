package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.services.ShareService;
import guru.bonacci.ninetags2.webdomain.ShareDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor
@Api(value = "Shares", description = "Offers share services")
public class ShareController {

	private final ShareService service;


	@ApiOperation(value = "Save share connected to tags and user. Possible to add non-existing topics (also relates the topics to each other)")
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> insert(final Share share) {
    	return null;
    }


	// curl -X POST -H 'Dear-User: Alpha' -H 'Content-Type: application/json' -i
	// http://localhost:8080/shares/private --data '{"title":"some
	// title","url":"https://www.some-url.com/","topics":["On Cooking","Non existing
	// topic"]}'
	@ApiOperation(value = "Save share connected to topics and user. Adds non-existing topics (also relates the topics to each other)")
    @PostMapping("/private")
    public CompletableFuture<ResponseEntity<?>> insertPrivate(@RequestBody final ShareDto share) {
		Share.ShareBuilder shareBuilder = Share.builder().title(share.getTitle()).url(share.getUrl());
		List<Topic> topics = share.getTopics().stream().map(t -> Topic.builder().name(t).build()).collect(toList());

		return service.insertPrivate(shareBuilder, topics)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	@ApiOperation(value = "Forshares. Possible to add non-existing topics (also relates the topics to each other)")
    @PostMapping("/for")
    public CompletableFuture<ResponseEntity<?>> forShare(final Share share, final _User user) {
    	return null;
    }

	@ApiOperation(value = "Mailing: mail to users (can include oneself -> that will become a private share)")
    @PostMapping("/mail")
    public CompletableFuture<ResponseEntity<?>> mail(final Share share, final List<_User> users) {
    	return null;
    }

	@ApiOperation(value = "Update share")
    @PutMapping("{id}")
    public CompletableFuture<ResponseEntity<?>> update() {
    	return null;
    }

}