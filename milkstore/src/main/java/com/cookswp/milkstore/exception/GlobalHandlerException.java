package com.cookswp.milkstore.exception;

import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalHandlerException extends RuntimeException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        System.out.println(exception.getBindingResult().getAllErrors().toString());
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorCodeException errorCode = mapToErrorCodeException(errors);
        ResponseError responseError = new ResponseError(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }


    //phương thức để ánh xạ lỗi thành  ErrorCodeException
    private ErrorCodeException mapToErrorCodeException(Map<String, String> errors) {
        for(String fieldName : errors.keySet()) {
            switch (fieldName){
                case "userID":{
                    return ErrorCodeException.USER_ID_NULL;
                }
            }
        }
        return ErrorCodeException.UNKNOWN_ERROR;
    }

}
