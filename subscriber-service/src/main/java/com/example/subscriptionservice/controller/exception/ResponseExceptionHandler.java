package com.example.subscriptionservice.controller.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolationException(ConstraintViolationException ex) {
        List<Violation> violations = ex.getConstraintViolations()
                .stream()
                .map(violation ->
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage())
                )
                .toList();

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "invalid data",
                violations
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Violation> violations = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError ->
                        new Violation(fieldError.getField(), fieldError.getDefaultMessage())
                )
                .toList();

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "invalid data",
                violations
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException ex) throws JsonProcessingException {
        return switch (ex.status()) {
            case 404, 400, 409 -> new ResponseEntity<>(new ObjectMapper().registerModule(new JavaTimeModule())
                    .readValue(ex.contentUTF8(), ErrorMessage.class), HttpStatus.valueOf(ex.status()));
            default -> new ResponseEntity<>(new ErrorMessage(
                    HttpStatus.valueOf(ex.status()),
                    null,
                    null),
                    HttpStatus.valueOf(ex.status()));
        };
    }
}
