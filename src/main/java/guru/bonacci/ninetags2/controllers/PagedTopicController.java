package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.Whelper.handleFailure;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.services.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topics/paged")
@RequiredArgsConstructor
@Api(value = "Topics on page", description = "Offers paged topic services")
public class PagedTopicController {

	private final TopicService service;

	
	@ApiOperation(value = "Topics that the user follows")
    @GetMapping("/followed")
    public CompletableFuture<ResponseEntity<?>> getFollowed(final Pageable page) {
		return service.getFollowed(page)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	@ApiOperation(value = "Place to be.. most liked topics today")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotTopics(final Pageable page) {
    	return null;
    }
}