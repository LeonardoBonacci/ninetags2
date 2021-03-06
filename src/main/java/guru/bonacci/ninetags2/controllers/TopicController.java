package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;
import static java.util.stream.Collectors.toSet;

import java.util.concurrent.CompletableFuture;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Interests;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.services.TopicService;
import guru.bonacci.ninetags2.services.UserService;
import guru.bonacci.ninetags2.validation.CheckOrder;
import guru.bonacci.ninetags2.webdomain.TopicDtoList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Validated
@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@Api(value = "Topics", description = "Offers topic services")
public class TopicController {

	private final UserService userService;
	private final TopicService topicService;

	
	// curl -X GET -H 'Dear-User: Alpha' -i http://localhost:8080/topics/list/Culture
	@ApiOperation(value = "List topics")
    @GetMapping("/list/{name}")
    public CompletableFuture<ResponseEntity<?>> list(@NotBlank @PathVariable("name") final String name) {
		return topicService.retrieveByName(name)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	@ApiOperation(value = "add topic to follow")
    @PostMapping("/follow/{topic}")
    public CompletableFuture<ResponseEntity<?>> follow(@NotBlank @PathVariable("topic") String topic) {
    	return userService.interests(topic)
    			.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
    }
	
	@ApiOperation(value = "remove topic to follow")
    @PostMapping("/unfollow/{topic}")
    public CompletableFuture<ResponseEntity<?>> unfollow(@NotBlank @PathVariable("topic") String topic) {
    	return userService.uninterests(topic)
    			.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
    }
	
	
	@ApiOperation(value = "updates the topic/interests-order")
    @PutMapping("/prio")
    public CompletableFuture<ResponseEntity<?>> prioritizeInterests(@Validated(CheckOrder.class) @RequestBody final TopicDtoList topics) {
		val interests = topics.stream()
						.map(t -> Interests.builder().followed(new Topic(t.getTopic())).prio(t.getPrio()).build())
						.collect(toSet());
		
		return topicService.prioritize(interests)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }
	
	
    @GetMapping("/recommend")
    public CompletableFuture<ResponseEntity<?>> recommend() {
		return topicService.recommend()
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }
}