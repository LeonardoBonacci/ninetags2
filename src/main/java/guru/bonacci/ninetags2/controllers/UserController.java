package guru.bonacci.ninetags2.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain._User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@Api(value = "Followed users", description = "Offers followed user services")
public class UserController {


	@ApiOperation(value = "Get followers, for mailing service")
    @GetMapping("/followers")
    public CompletableFuture<ResponseEntity<?>> getFollowers() {
    	return null;
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