package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@ControllerAdvice
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserRegistrationDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        if (userService.checkEmailExistence(userRegistrationDTO.getEmailAddress()))
            throw new DataIntegrityViolationException("Email existed, please try with another email");
        else {
            userService.registerUser(userRegistrationDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Registration successfully!", userRegistrationDTO);
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<UserRegistrationDTO> handleEmailDuplicationException(DataIntegrityViolationException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }
}
