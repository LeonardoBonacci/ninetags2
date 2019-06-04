package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.services.UserService;
import guru.bonacci.ninetags2.webdomain.PageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/paged")
@RequiredArgsConstructor
@Api(value = "Users on page", description = "Offers paged followed user services")
public class PagedUserController {

	private final UserService service;

	
	@ApiOperation(value = "Other users that the user follows")
    @GetMapping("/followed")
    public CompletableFuture<ResponseEntity<?>> getFollowed(final Pageable pageRequest) {
		return service.retrieveFollowed(pageRequest)
				.thenApply(results -> new PageDto<_User>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	@ApiOperation(value = "Hall of fame.. most liked users today")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotUsers(final Pageable pageable) {
    	return null; //TODO
    }
}