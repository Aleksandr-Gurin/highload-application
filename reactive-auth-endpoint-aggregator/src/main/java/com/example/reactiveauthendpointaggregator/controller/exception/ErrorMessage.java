package com.example.reactiveauthendpointaggregator.controller.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorMessage {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;

    private List<Violation> violations;

    public ErrorMessage(HttpStatus httpStatus, String message) {
        this.timestamp = Instant.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpStatus httpStatus, String message, List<Violation> violations) {
        this(httpStatus, message);
        this.violations = violations;
    }
}
