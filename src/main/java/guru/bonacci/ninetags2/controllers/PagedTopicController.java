package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.services.TopicService;
import guru.bonacci.ninetags2.webdomain.PageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topics/paged")
@RequiredArgsConstructor
@Api(value = "Topics on page", description = "Offers paged topic services")
public class PagedTopicController {

	private final TopicService service;

	
	// curl -X GET -H 'Dear-User: Alpha' -i 'http://localhost:8080/topics/paged/followed?page=0&size=5'
	@ApiOperation(value = "Topics that the user follows")
    @GetMapping("/followed")
    public CompletableFuture<ResponseEntity<?>> getFollowed(final Pageable pageRequest) {
		return service.retrieveFollowed(pageRequest)
				.thenApply(results -> new PageDto<Topic>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	@ApiOperation(value = "Place to be.. most liked topics today")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotTopics(final Pageable pageRequest) {
		return service.retrieveHotTopics(pageRequest)
				.thenApply(results -> new PageDto<Topic>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }
	
	@ApiOperation(value = "Popular topics for this user...")
	@GetMapping("/active/{username}")
	public CompletableFuture<ResponseEntity<?>> findByTitle(@PathVariable("username") final String username, final Pageable pageRequest) {
		return service.retrieveMostSharingByUser(username, pageRequest)
				.thenApply(results -> new PageDto<Topic>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }
}