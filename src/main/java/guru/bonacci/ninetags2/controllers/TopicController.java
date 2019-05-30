package guru.bonacci.ninetags2.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Topic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@Api(value = "Topics", description = "Offers topic services")
public class TopicController {


	@ApiOperation(value = "updates the topic-order")
    @PutMapping
    public CompletableFuture<ResponseEntity<?>> prioritizeInterests(final List<Topic> topics) {
    	return null;
    }

	@ApiOperation(value = "add topic to follow")
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> follow(final Topic topic) {
    	return null;
    }
}