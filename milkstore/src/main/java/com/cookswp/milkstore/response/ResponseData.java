package com.cookswp.milkstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    @Getter @Setter
    private int code;

    @Getter @Setter
    private String message;

    @Setter
    private T data;

    public ResponseData() {}

    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
