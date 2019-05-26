package guru.bonacci.ninetags2.web;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Whelper { // WebHelper

    public Function<Throwable, ResponseEntity<?>> handleFailure = throwable -> {
        log.error("Unable to execute request, sorry!!", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
}
