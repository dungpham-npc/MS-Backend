//package com.cookswp.milkstore.controller;
//
//import com.cookswp.milkstore.model.UserRegistrationDTO;
//import com.cookswp.milkstore.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    private final UserService userService;
//
//    @Autowired
//    public LoginController(UserService userService){
//        this.userService = userService;
//    }
//
//    public String login(@RequestBody UserRegistrationDTO userRegistrationDTO){
//        return userService.loginUser(userRegistrationDTO) ? "true" : "false";
//    }
//}
