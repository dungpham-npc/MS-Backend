package com.cookswp.milkstore.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //Post Error Code Exception:
    TITLE_NULL(1, "Title cannot be null"),
    TITLE_BLANK(2, "Title cannot be blank"),
    TITLE_EMPTY(3, "Title cannot be empty"),
    CONTENT_NULL(4, "Content cannot be null"),
    CONTENT_BLANK(5, "Content cannot be blank"),
    CONTENT_EMPTY(6, "Content cannot be empty"),
    POST_ID_NOT_FOUND(7, "Post id not found"),
    UNKNOWN_ERROR(666, "Unknown error"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
