package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.services.PagedShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shares/users/paged")
@RequiredArgsConstructor
@Api(value = "Shares by users on page", description = "Offers paged share services by followed user")
public class PagedShareUserController {

	private final PagedShareService service;

	
	// curl -X GET -H 'Dear-User: Alpha' -i 'http://localhost:8080/shares/users/paged/perspective?page=0''
	@ApiOperation(value = "The famous user perspective")
	@GetMapping("/perspective")
    public CompletableFuture<ResponseEntity<?>> getUserPerspective(final Pageable pageRequest) {
		return service.retrieveUserPerspective(pageRequest)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }


	@ApiOperation(value = "The user AND topic interest")
	@GetMapping("/followed")
    public CompletableFuture<ResponseEntity<?>> getByFollowedAndTopics(final Pageable pageable) {
		// already part of perspective - implementation trivial
		return null;
    }

	@ApiOperation(value = "The user AND NO topic interest")
	@GetMapping("/nonfollowed")
    public CompletableFuture<ResponseEntity<?>> getByFollowedAndNonTopics(final Pageable pageable) {
		// already part of perspective - implementation trivial
		return null;
    }

	@ApiOperation(value = "Most liked shares today by followed user")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotShares(final Pageable pageable) {
		// already part of perspective - implementation trivial
		return null;
    }
}