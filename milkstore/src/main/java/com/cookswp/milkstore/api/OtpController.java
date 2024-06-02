package com.cookswp.milkstore.api;

import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@EnableWebSecurity
public class OtpController {
    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService){
        this.otpService = otpService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> initializeOtp(){
        return new ResponseData<>(HttpStatus.OK.value(), "OTP initialized successfully!", null);
    }
}
