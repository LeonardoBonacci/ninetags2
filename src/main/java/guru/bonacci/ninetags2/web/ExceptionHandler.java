package guru.bonacci.ninetags2.web;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.function.Function;

import org.springframework.http.ResponseEntity;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ExceptionHandler {

    public Function<Throwable, ResponseEntity<?>> handleFailure = throwable -> {
        log.error("Unable to execute your noble request, sorry!!", throwable);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    };
}
