package com.cookswp.milkstore.api;

import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.exception.RoleNotFoundException;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper){
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/staffs")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<UserRegistrationDTO>> getStaffList(){
        return new ResponseData<>(HttpStatus.OK.value(),
                "List retrieved successfully!",
                userService.getInternalUserList().stream()
                        .map(user -> mapper.map(user, UserRegistrationDTO.class))
                        .toList());
    }

    @GetMapping("/members")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<UserDTO>> getMemberList(){
        return new ResponseData<>(HttpStatus.OK.value(),
                "List retrieved successfully!",
                userService.getMemberUserList().stream()
                        .map(user -> mapper.map(user, UserDTO.class))
                        .toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserDTO> createStaff(UserRegistrationDTO userRegistrationDTO){
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

        User user = userService.registerStaff(mapper.map(userRegistrationDTO, User.class));

        return new ResponseData<>(HttpStatus.CREATED.value(),
                "User created successfully!",
                mapper.map(user, UserDTO.class));

    }

    @PutMapping("/staffs/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserDTO> updateStaff(@PathVariable int id, UserRegistrationDTO userRegistrationDTO){
        if (userRegistrationDTO.getEmailAddress() == null ||
                userRegistrationDTO.getPassword() == null ||
                userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        Set<String> allowedRoles = Set.of("MANAGER", "POST STAFF", "PRODUCT STAFF", "SELLER");
        if (!allowedRoles.contains(userRegistrationDTO.getRoleName())) {
            throw new RoleNotFoundException();
        }

        userService.updateUserBasicInformation(id, mapper.map(userRegistrationDTO, User.class));
        return new ResponseData<>(HttpStatus.OK.value(),
                "User updated successfully!",
                mapper.map(userRegistrationDTO, UserDTO.class));
    }

    @DeleteMapping("/staffs/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> deleteStaff(@PathVariable int id){
        userService.deleteUser(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Staff deleted successfully!", null);
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
