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
@RequestMapping("/users/paged")
@RequiredArgsConstructor
@Api(value = "Users on page", description = "Offers paged followed user services")
public class PagedUserController {


	@ApiOperation(value = "Topics that the user follows")
	@GetMapping
    public CompletableFuture<ResponseEntity<?>> getFollowed(final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "Most liked users")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotUsers(final Pageable pageable) {
    	return null;
    }
}