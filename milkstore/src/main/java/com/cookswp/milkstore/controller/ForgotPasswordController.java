package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot")
@ControllerAdvice
public class ForgotPasswordController {
    private final UserService userService;
    public ForgotPasswordController(UserService userService){
        this.userService = userService;
    }
//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<UserDTO> changePassword(@RequestBody String newPassword){
//
//    }
}
