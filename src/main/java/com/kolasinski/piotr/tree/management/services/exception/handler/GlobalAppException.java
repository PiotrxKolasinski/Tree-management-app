package com.kolasinski.piotr.tree.management.services.exception.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class GlobalAppException extends RuntimeException {
    private String message;
    private GlobalAppExceptionCode code;
    private Instant timestamp;
    private final List<String> args;

    public GlobalAppException(String message, GlobalAppExceptionCode code, List<String> args) {
        super(message);
        this.message = message;
        this.code = code;
        this.timestamp = Instant.now();
        this.args = args;
    }
}

