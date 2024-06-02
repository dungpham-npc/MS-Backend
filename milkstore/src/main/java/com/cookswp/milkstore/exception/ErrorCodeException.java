package com.cookswp.milkstore.exception;

public enum ErrorCodeException {
    //Post Error Code Exception:

    USER_ID_NULL(001, "UserID can not be null"),
    UNKNOWN_ERROR(666, "Unknown error"),;

    private int code;
    private String message;

    ErrorCodeException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
