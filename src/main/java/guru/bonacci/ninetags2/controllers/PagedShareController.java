package guru.bonacci.ninetags2.controllers;

import static guru.bonacci.ninetags2.web.ExceptionHandler.handleFailure;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.services.PagedShareService;
import guru.bonacci.ninetags2.webdomain.PageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shares/paged")
@RequiredArgsConstructor
@Api(value = "Shares on page", description = "Offers paged share services")
public class PagedShareController {

	private final PagedShareService service;


	// curl -X GET -H 'Dear-User: Alpha' -i 'http://localhost:8080/shares/paged/private?page=0&size=5'
	@ApiOperation(value = "Bookmarks: retrieve your shares. No other user has access to these shares")
    @GetMapping("/private")
    public CompletableFuture<ResponseEntity<?>> getShares(final Pageable pageRequest) {
		return service.getPrivateShares(pageRequest)
				.thenApply(results -> new PageDto<Share>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	// curl -X GET -H 'Dear-User: Alpha' -i http://localhost:8080/shares/paged/sent
	@ApiOperation(value = "Mailing/directed shares: sent-items")
    @GetMapping("/sent")
    public CompletableFuture<ResponseEntity<?>> getSentDirectedShares(final Pageable pageRequest) {
		return service.getSentDirectedShares(pageRequest)
				.thenApply(results -> new PageDto<Share>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	// curl -X GET -H 'Dear-User: Beta' -i http://localhost:8080/shares/paged/inbox
	@ApiOperation(value = "Mailing/directed shares: inbox")
    @GetMapping("/inbox")
    public CompletableFuture<ResponseEntity<?>> getReceivedDirectedShares(final Pageable pageRequest) {
		return service.getReceivedDirectedShares(pageRequest)
				.thenApply(results -> new PageDto<Share>(results.getContent()))
		        .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
		        .exceptionally(handleFailure);
    }

	@ApiOperation(value = "Most liked shares today")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotShares(final Pageable pageable) {
    	return null;
    }

}