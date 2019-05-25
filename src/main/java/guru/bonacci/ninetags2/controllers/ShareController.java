package guru.bonacci.ninetags2.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.services.PagedShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor
@Api(value = "Shares", description = "Offers share services")
public class ShareController {

    private final PagedShareService service;
 

    @GetMapping("/test")
    public CompletableFuture<ResponseEntity<?>> test() {
        return service
                .findAll(null)
                .<ResponseEntity<?>>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetSharesFailure);
    }
    
    private static Function<Throwable, ResponseEntity<?>> handleGetSharesFailure = throwable -> {
        log.error("Unable to retrieve shares", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

	@ApiOperation(value = "Save share connected to tags and user. Possible to add non-existing topics (also relates the topics to each other)")
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> insert(final Share share) {
    	return null;
    }

	@ApiOperation(value = "Forshares. Possible to add non-existing topics (also relates the topics to each other)")
    @PostMapping("/for")
    public CompletableFuture<ResponseEntity<?>> forShare(final Share share, final _User user) {
    	return null;
    }

	@ApiOperation(value = "Mailing: mail to users (can include oneself)")
    @PostMapping("/mail")
    public CompletableFuture<ResponseEntity<?>> mail(final Share share, final List<_User> users) {
    	return null;
    }

	@ApiOperation(value = "Update share")
    @PutMapping("{id}")
    public CompletableFuture<ResponseEntity<?>> update() {
    	return null;
    }

}