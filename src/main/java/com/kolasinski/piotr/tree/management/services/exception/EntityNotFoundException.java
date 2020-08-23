package com.kolasinski.piotr.tree.management.services.exception;

import com.kolasinski.piotr.tree.management.services.exception.handler.GlobalAppException;
import com.kolasinski.piotr.tree.management.services.exception.handler.GlobalAppExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends GlobalAppException {

    public EntityNotFoundException(String message, List<String> args) {
        super(message, GlobalAppExceptionCode.ENTITY_NOT_FOUND, args);
    }

    public EntityNotFoundException(String message) {
        this(message, null);
    }
}
