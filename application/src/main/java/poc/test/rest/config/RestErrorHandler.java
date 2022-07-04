package poc.test.rest.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import poc.test.domain.exceptions.EntityNotFoundException;

@RestControllerAdvice
class RestErrorHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}
