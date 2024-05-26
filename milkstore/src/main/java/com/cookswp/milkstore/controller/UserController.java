package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.exception.RoleNotFoundException;
import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/staffs")
    public List<UserRegistrationDTO> getInternalUserList(){
        return userService.getInternalUserList();
    }

    @GetMapping("/members")
    public List<UserRegistrationDTO> getMemberUserList(){
        return userService.getMemberUserList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        if (userService.checkEmailExistence(userRegistrationDTO.getEmailAddress()))
            throw new DataIntegrityViolationException("Email existed, please try with another email");

        if (userRegistrationDTO.getEmailAddress() == null ||
        userRegistrationDTO.getPassword() == null ||
        userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        Set<String> allowedRoles = Set.of("MANAGER", "POST STAFF", "PRODUCT STAFF", "SELLER");
        if (!allowedRoles.contains(userRegistrationDTO.getRoleName())) {
            throw new RoleNotFoundException();
        }

        userService.registerStaff(userRegistrationDTO);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userRegistrationDTO.getUsername());
        userDTO.setEmailAddress(userRegistrationDTO.getEmailAddress());
        userDTO.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        return new ResponseData<>(HttpStatus.CREATED.value(), "User created successfully!", userDTO);

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

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<UserRegistrationDTO> handleRoleNotFoundException(RoleNotFoundException e){
        return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

}
