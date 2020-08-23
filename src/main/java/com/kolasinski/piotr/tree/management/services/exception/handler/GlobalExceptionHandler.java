package com.kolasinski.piotr.tree.management.services.exception.handler;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionHandlerResponse> handleException(GlobalAppException ex) {
        ExceptionHandlerResponse errorResponse = ExceptionHandlerResponse.builder()
                .status(getStatus(ex))
                .message(ex.getMessage())
                .code(ex.getCode())
                .timestamp(ex.getTimestamp())
                .args(ex.getArgs())
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionHandlerResponse> handleException(Exception ex) {
        ex.printStackTrace();
        ExceptionHandlerResponse errorResponse = ExceptionHandlerResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .code(GlobalAppExceptionCode.BAD_REQUEST)
                .timestamp(Instant.now())
                .args(null)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private HttpStatus getStatus(Exception ex) {
        return Stream.of(ex.getClass().getAnnotations())
                .filter(annotation -> annotation instanceof ResponseStatus)
                .map(ResponseStatus.class::cast)
                .map(ResponseStatus::value)
                .findFirst().orElse(HttpStatus.BAD_REQUEST);
    }

    @Builder
    @Getter
    private static class ExceptionHandlerResponse {
        private final HttpStatus status;
        private final String message;
        private final GlobalAppExceptionCode code;
        private final Instant timestamp;
        private final List<String> args;
    }
}
