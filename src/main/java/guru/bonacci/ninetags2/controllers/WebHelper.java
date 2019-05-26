package guru.bonacci.ninetags2.controllers;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebHelper {

    static Function<Throwable, ResponseEntity<?>> handleFailure = throwable -> {
        log.error("Unable to execute request, sorry!!", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
}
