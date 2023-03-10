package authors.controller.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Objects;


@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(NotFoundException ex) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleEntityExistsException(EntityExistsException ex) {
        return new ErrorMessage(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }


    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolationException(WebExchangeBindException ex) {
        log.info("ERROR HANDLER");

        List<Violation> violations = ex.getFieldErrors()
                .stream()
                .map(violation ->
                        new Violation(violation.getField(), violation.getDefaultMessage())
                )
                .toList();

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "invalid data",
                violations
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(DataIntegrityViolationException ex) {
        log.info(ex.getMessage());
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                Objects.requireNonNull(ex.getMessage()).split(";")[2]
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException ex) {
        return switch (ex.status()) {
            case 404 -> new ResponseEntity<>(new ErrorMessage(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage(),
                    null), HttpStatus.NOT_FOUND);
            case 503 -> new ResponseEntity<>(new ErrorMessage(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    ex.getMessage(),
                    null),
                    HttpStatus.SERVICE_UNAVAILABLE);
            default -> new ResponseEntity<>(new ErrorMessage(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
