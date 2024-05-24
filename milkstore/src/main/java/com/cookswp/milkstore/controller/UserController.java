package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserRegistrationDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        userService.registerUser(userRegistrationDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Registration successfully!", userRegistrationDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return userService.loginUser(userRegistrationDTO) ? "true" : "false";
    }
}
