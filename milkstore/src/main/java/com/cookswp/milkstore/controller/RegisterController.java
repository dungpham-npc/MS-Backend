package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.exception.RoleNotFoundException;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public ResponseData<UserDTO> register(UserRegistrationDTO userRegistrationDTO){
        if (userService.checkEmailExistence(userRegistrationDTO.getEmailAddress()))
            throw new DataIntegrityViolationException("Email existed, please try with another email");

        if (userRegistrationDTO.getEmailAddress() == null ||
                userRegistrationDTO.getPassword() == null ||
                userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        userService.registerUser(userRegistrationDTO);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userRegistrationDTO.getUsername());
        userDTO.setEmailAddress(userRegistrationDTO.getEmailAddress());
        userDTO.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        return new ResponseData<>(HttpStatus.CREATED.value(), "Registration successfully!", userDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<UserRegistrationDTO> handleEmailDuplicationException(DataIntegrityViolationException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }
}
