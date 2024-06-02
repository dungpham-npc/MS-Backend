package com.cookswp.milkstore.api;

import com.cookswp.milkstore.exception.RoleNotFoundException;
import com.cookswp.milkstore.pojo.dtos.UserModel.TemporaryUserDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.OtpService;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {
    private final OtpService otpService;
    private final UserService userService;

    @Autowired
    public OtpController(OtpService otpService, UserService userService){
        this.otpService = otpService;
        this.userService = userService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> sendRegistrationOtp(String email){
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email address cannot be null or empty.");
        if (userService.getUserByEmail(email) != null)
            throw new DataIntegrityViolationException("Account with this email already existed!");
        otpService.sendRegistrationOtpByEmail(email);
        return new ResponseData<>(HttpStatus.OK.value(), "OTP initialized successfully!", null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> verifyOtpCode(TemporaryUserDTO temporaryUserDTO){
        if (!otpService.isOtpValid(temporaryUserDTO.getEmailAddress(), temporaryUserDTO.getOtpCode()))
            throw new IllegalArgumentException("OTP is incorrect");

        otpService.deleteTempUser(temporaryUserDTO.getEmailAddress());
        return new ResponseData<>(HttpStatus.OK.value(), "OTP validated successfully!", null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<String> handleRoleNotFoundException(IllegalArgumentException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }
}
