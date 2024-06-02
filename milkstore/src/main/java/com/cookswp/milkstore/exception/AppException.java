package com.cookswp.milkstore.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCodeException errorCodeException;

    public AppException(ErrorCodeException errorCodeException) {
        super(errorCodeException.getMessage());
        this.errorCodeException = errorCodeException;
    }

}
