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
@RequestMapping("/shares/topics/paged")
@RequiredArgsConstructor
@Api(value = "Shares by topics on page", description = "Offers paged share services by topic")
public class PagedShareTopicController {

	private final PagedShareService service;

	
	// curl -X GET -H 'Dear-User: Alpha' -i 'http://localhost:8080/shares/topics/paged/perspective?page=0''
	@ApiOperation(value = "The famous topic perspective")
	@GetMapping("/perspective")
    public CompletableFuture<ResponseEntity<?>> getTopicPerspective(final Pageable pageRequest) {
		return service.retrieveTopicPerspective(pageRequest)
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	
	@ApiOperation(value = "The topic AND user interest")
	@GetMapping("/followed")
    public CompletableFuture<ResponseEntity<?>> getByTopicsAndFollowed(final Pageable pageable) {
		// already part of perspective - implementation trivial
    	return null;
    }

	@ApiOperation(value = "The topic AND NO user interest")
	@GetMapping("/nonfollowed")
    public CompletableFuture<ResponseEntity<?>> getByTopicsAndNonFollowed(final Pageable pageable) {
		// already part of perspective - implementation trivial
    	return null;
    }

	@ApiOperation(value = "Most liked shares today by topic")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotShares(final Pageable pageable) {
		// already part of perspective - implementation trivial
    	return null;
    }
}