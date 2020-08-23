package com.kolasinski.piotr.tree.management.services.treenode.exception;

import com.kolasinski.piotr.tree.management.services.exception.handler.GlobalAppException;
import com.kolasinski.piotr.tree.management.services.exception.handler.GlobalAppExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.CONFLICT)
public class RootTreeNodeExistsException extends GlobalAppException {

    public RootTreeNodeExistsException(String message, List<String> args) {
        super(message, GlobalAppExceptionCode.ROOT_TREE_NODE_ALREADY_EXISTS, args);
    }

    public RootTreeNodeExistsException(String message) {
        this(message, null);
    }
}
