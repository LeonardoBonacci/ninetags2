package guru.bonacci.ninetags2.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain._User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shares/paged")
@RequiredArgsConstructor
@Api(value = "Shares on page", description = "Offers paged share services")
public class PagedShareController {

	
	@ApiOperation(value = "Bookmarks: retrieve your shares. No other user has access to these shares")
	@GetMapping
    public CompletableFuture<ResponseEntity<?>> getShares(final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "Mailing: sent-items")
    @GetMapping("/sent")
    public CompletableFuture<ResponseEntity<?>> getPrivatelySentShares(final _User user, final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "Mailing: inbox")
    @GetMapping("/inbox")
    public CompletableFuture<ResponseEntity<?>> getPrivatelyReceivedShares(final _User user, final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "Most liked shares today")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotShares(final _User user, final Pageable pageable) {
    	return null;
    }

}