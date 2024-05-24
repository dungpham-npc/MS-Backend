package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.model.UserRegistrationDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    public String login(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return userService.loginUser(userRegistrationDTO) ? "true" : "false";
    }
}
