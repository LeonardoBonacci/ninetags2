package guru.bonacci.ninetags2.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.services.UserService;
import guru.bonacci.ninetags2.web.Whelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(value = "Followed users", description = "Offers followed user services")
public class UserController {

	private final UserService service;
	
	
	@ApiOperation(value = "Get the user")
    @GetMapping("/{name}")
    public CompletableFuture<ResponseEntity<?>> getByName(@PathVariable("name") String name) {
		return service.getByName(name)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(Whelper.handleFailure);
    }
	
	@ApiOperation(value = "Get followers, for mailing service")
    @GetMapping("/followers")
    public CompletableFuture<ResponseEntity<?>> getFollowers() {
		return service.getFollowed()
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(Whelper.handleFailure);
    }

    @ApiOperation(value = "add user to follow")
    @PostMapping("/shares")
    public CompletableFuture<ResponseEntity<?>> follow(final _User toBeFollowed) {
    	return null;
    }

    @ApiOperation(value = "updates the followed-order")
    @PutMapping
    public CompletableFuture<ResponseEntity<?>> prioritizeFollowed(final List<_User> followed) {
    	return null;
    }
}