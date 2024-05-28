package com.cookswp.milkstore.api;

import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.pojo.dtos.UserDTO;
import com.cookswp.milkstore.pojo.dtos.UserRegistrationDTO;
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
    public ResponseData<UserDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        if (userService.checkEmailExistence(userRegistrationDTO.getEmailAddress()))
            throw new DataIntegrityViolationException("Email existed, please try with another email");

        if (userRegistrationDTO.getEmailAddress() == null ||
                userRegistrationDTO.getPassword() == null ||
                userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        userService.registerUser(userRegistrationDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(),
                "Registration successfully!",
                new UserDTO(userRegistrationDTO.getEmailAddress(),
                        userRegistrationDTO.getPhoneNumber(),
                        userRegistrationDTO.getUsername()));
    }

    @PostMapping("/complete-registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserDTO> completeRegister(@RequestParam String email, @RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {
        UserRegistrationDTO user = userService.getUserByEmail(email);
        if (user == null)
            throw new Exception("Error processing the request");

        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        userService.updateUser(user.getUserId(), user);

        return new ResponseData<>(HttpStatus.CREATED.value(),
                "Registration completed!",
                new UserDTO(user.getEmailAddress(),
                        user.getPhoneNumber(),
                        user.getUsername()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<UserRegistrationDTO> handleEmailDuplicationException(DataIntegrityViolationException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }
    @ExceptionHandler(MissingRequiredFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<UserRegistrationDTO> handleNullFieldsException(MissingRequiredFieldException e){
        return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }
}
