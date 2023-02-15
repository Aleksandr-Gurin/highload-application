package com.example.authendpointaggregator.controller.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException ex) throws JsonProcessingException {
        log.info(ex.getMessage());
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
