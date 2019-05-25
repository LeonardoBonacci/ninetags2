package guru.bonacci.ninetags2.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topics/paged")
@RequiredArgsConstructor
@Api(value = "Topics on page", description = "Offers paged topic services")
public class PagedTopicController {


	@ApiOperation(value = "Topics that the user follows")
	@GetMapping
    public CompletableFuture<ResponseEntity<?>> getFollowed(final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "Place to be.. most liked topics today")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotTopics(final Pageable pageable) {
    	return null;
    }
}