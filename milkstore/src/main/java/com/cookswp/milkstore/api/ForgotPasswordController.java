package com.cookswp.milkstore.api;

import com.cookswp.milkstore.service.UserService;
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
