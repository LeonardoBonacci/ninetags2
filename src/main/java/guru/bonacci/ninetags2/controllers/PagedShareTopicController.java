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
@RequestMapping("/shares/topics/paged")
@RequiredArgsConstructor
@Api(value = "Shares by topics on page", description = "Offers paged share services by topic")
public class PagedShareTopicController {


	@ApiOperation(value = "The famous topic perspective")
	@GetMapping("/perspective")
    public CompletableFuture<ResponseEntity<?>> getTopicPerspective(final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "The topic AND user interest")
	@GetMapping("/followed")
    public CompletableFuture<ResponseEntity<?>> getByTopicsAndFollowed(final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "The topic AND NO user interest")
	@GetMapping("/nonfollowed")
    public CompletableFuture<ResponseEntity<?>> getByTopicsAndNonFollowed(final Pageable pageable) {
    	return null;
    }

	@ApiOperation(value = "Most liked shares today by topic")
    @GetMapping("/hot")
    public CompletableFuture<ResponseEntity<?>> getHotShares(final Pageable pageable) {
    	return null;
    }
}