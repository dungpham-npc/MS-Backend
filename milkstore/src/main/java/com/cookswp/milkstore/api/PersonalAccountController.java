package com.cookswp.milkstore.api;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@EnableWebSecurity
public class PersonalAccountController {


}
