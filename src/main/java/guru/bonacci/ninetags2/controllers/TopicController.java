package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@Api(value = "Topics", description = "Offers topic services")
public class TopicController {

	private final UserService userService;

	
	@ApiOperation(value = "updates the topic-order")
    @PutMapping
    public CompletableFuture<ResponseEntity<?>> prioritizeInterests(final List<Topic> topics) {
    	return null;
    }


	@ApiOperation(value = "add topic to follow")
    @PostMapping("/follow/{topic}")
    public CompletableFuture<ResponseEntity<?>> follow(@PathVariable("topic") String topic) {
    	return userService.interests(topic)
    			.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
    }
	
	@ApiOperation(value = "remove topic to follow")
    @PostMapping("/unfollow/{topic}")
    public CompletableFuture<ResponseEntity<?>> unfollow(@PathVariable("topic") String topic) {
    	return userService.uninterests(topic)
    			.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
    }
}