package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.services.ShareService;
import guru.bonacci.ninetags2.webdomain.DirectedShareDto;
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

	
	@ApiOperation(value = "For testing purposes, find by title 'like'")
	@GetMapping("/{title}")
	public CompletableFuture<ResponseEntity<?>> findByTitle(@PathVariable("title") final String title) {
		return service.findByTitle(title)
					.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
					.exceptionally(handleFailure);
	}

	
	// curl -X POST -H 'Dear-User: Alpha' -H 'Content-Type: application/json' -i
	// http://localhost:8080/shares/ --data
	// '{"title":"anothertitle","url":"https://www.encyclo.nl/begrip/bla","topics":["Foo","Another
	// topic"]}'
	@ApiOperation(value = "Saves share connected to tags and user. Adds non-existing topics")
	@PostMapping
	public CompletableFuture<ResponseEntity<?>> insert(@Valid @RequestBody final ShareDto share) {
		Share.ShareBuilder shareBuilder = Share.builder().title(share.getTitle()).url(share.getUrl());
		List<Topic> topics = share.getTopics().stream().map(t -> Topic.builder().name(t).build()).collect(toList());

		return service.insert(shareBuilder, topics)
				.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
	}

	
	// curl -X POST -H 'Content-Type: application/json' -H 'Dear-User: Alpha' -i
	// http://localhost:8080/shares/private --data
	// '{"title":"anothertitle","url":"https://www.encyclo.nl/begrip/bla","topics":["Foo","antopic","btopic",
	// "ctopic"]}'
	@ApiOperation(value = "Saves private share. Adds non-existing topics.")
	@PostMapping("/private")
	public CompletableFuture<ResponseEntity<?>> insertPrivate(@Valid @RequestBody final ShareDto share) {
		Share.ShareBuilder shareBuilder = Share.builder().title(share.getTitle()).url(share.getUrl());
		List<Topic> topics = share.getTopics().stream().map(t -> Topic.builder().name(t).build()).collect(toList());

		return service.insertPrivate(shareBuilder, topics)
				.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
	}


	// curl -X POST -H 'Dear-User: Alpha' -H 'Content-Type: application/json' -i
	// http://localhost:8080/shares/directed --data
	// '{"title":"sometitle","url":"https://www.some-url.com/","topics":["On
	// Cooking","Non existing topic"],"users":["Beta","Gamma"]}'
	@ApiOperation(value = "Directed share: send to users (can include oneself -> that will become a private share)")
	@PostMapping("/directed")
	public CompletableFuture<ResponseEntity<?>> insertDirected(@Valid @RequestBody final DirectedShareDto share) {
		Share.ShareBuilder shareBuilder = Share.builder().title(share.getTitle()).url(share.getUrl());
		List<Topic> topics = share.getTopics().stream().map(t -> Topic.builder().name(t).build()).collect(toList());

		return service.insertDirected(shareBuilder, topics, share.getUsers())
				.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
	}

	
	@ApiOperation(value = "I Like. Just that.")
	@PostMapping("/like/{shareId}")
	public CompletableFuture<ResponseEntity<?>> like(@PathVariable("shareId") final Long shareId) {
		return service.like(shareId)
				.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
	}

	
	@ApiOperation(value = "Forshares. Possible to add non-existing topics (also relates the topics to each other)")
	@PostMapping("/for")
	public CompletableFuture<ResponseEntity<?>> forShare(final Share share, final _User user) {
		return null; //TODO
	}

	
	// curl -X PUT -H 'Dear-User: Alpha' -H 'Content-Type: application/json' -i
	// http://localhost:8080/shares/200 --data
	// '{"title":"anortitle","url":"https://www.encyclo.nl/begrip/bla","topics":["Foo"]}'
	@ApiOperation(value = "Update share")
	@PutMapping("/{id}")
	public CompletableFuture<ResponseEntity<?>> update(@PathVariable("id") final Long id, @RequestBody final ShareDto share) {
		Share.ShareBuilder shareBuilder = Share.builder().title(share.getTitle()).url(share.getUrl()).id(id);
		List<Topic> topics = share.getTopics().stream().map(t -> Topic.builder().name(t).build()).collect(toList());

		return service.update(shareBuilder, topics)
				.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
	}

	
	// curl -X DELETE -H 'Dear-User: Alpha' -i http://localhost:8080/shares/200
	@ApiOperation(value = "Delete share")
	@DeleteMapping("{id}")
	public CompletableFuture<ResponseEntity<?>> delete(@PathVariable("id") final Long id) {
		return service.delete(id)
				.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
	}
}