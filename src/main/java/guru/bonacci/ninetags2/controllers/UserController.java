package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(value = "Followed users", description = "Offers followed user services")
public class UserController {

	private final UserService userService;

	
	// curl -X GET -H 'Dear-User: Alpha' -i http://localhost:8080/users/Alpha
	@ApiOperation(value = "Get the user")
    @GetMapping("/{name}")
    public CompletableFuture<ResponseEntity<?>> getByName(@PathVariable("name") String name) {
		return userService.retrieveByName(name)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	// curl -X GET -H 'Dear-User: Alpha' -i http://localhost:8080/users/followers
	@ApiOperation(value = "Get followers, for mailing service")
    @GetMapping("/followers")
    public CompletableFuture<ResponseEntity<?>> getFollowers() {
		return userService.retrieveFollowed()
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }


	@ApiOperation(value = "add user to follow")
    @PostMapping("/follow/{name}")
    public CompletableFuture<ResponseEntity<?>> follow(@PathVariable("name") String username) {
    	return userService.follows(username)
    			.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
    }
	
	@ApiOperation(value = "remove topic to follow")
    @PostMapping("/unfollow/{name}")
    public CompletableFuture<ResponseEntity<?>> unfollow(@PathVariable("name") String username) {
    	return userService.unfollows(username)
    			.<ResponseEntity<?>>thenApply(ResponseEntity::ok)
				.exceptionally(handleFailure);
    }

    
    @ApiOperation(value = "updates the followed-order")
    @PutMapping
    public CompletableFuture<ResponseEntity<?>> prioritizeFollowed(final List<_User> followed) {
    	return null;
    }
}