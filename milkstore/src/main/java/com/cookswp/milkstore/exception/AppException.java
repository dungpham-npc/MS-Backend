package com.cookswp.milkstore.exception;

public class AppException extends RuntimeException {

    private final ErrorCodeException errorCodeException;

    public AppException(ErrorCodeException errorCodeException) {
        super(errorCodeException.getMessage());
        this.errorCodeException = errorCodeException;
    }

    public ErrorCodeException getErrorCodeException() {
        return errorCodeException;
    }
}
