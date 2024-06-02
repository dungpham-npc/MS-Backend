package com.cookswp.milkstore.api;


import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.pojo.dtos.UserModel.CustomUserDetails;
import com.cookswp.milkstore.pojo.dtos.UserModel.PasswordUpdateDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@EnableWebSecurity
public class PersonalAccountController {
    private final UserService userService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonalAccountController(UserService userService,
                                     ModelMapper mapper,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserRegistrationDTO> getUserProfile(){
        return new ResponseData<>(HttpStatus.OK.value(),
                mapper.map(userService.getCurrentUser(),
                        UserRegistrationDTO.class) == null ?
                        "User has not logged in" :
                        "User profile retrieved",
                mapper.map(userService.getCurrentUser(), UserRegistrationDTO.class));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserDTO> updatePersonalProfileInformation(UserRegistrationDTO userRegistrationDTO){
        User user = userService.getUserByEmail(userRegistrationDTO.getEmailAddress());
        if (userRegistrationDTO.getEmailAddress() == null ||
                userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");
        userService.updateUser(user.getUserId(), user);
        return new ResponseData<>(HttpStatus.OK.value(), "Information updated successfully!", mapper.map(user, UserDTO.class));
    }

//    @PutMapping("/password-update")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<String> updateUserPassword(PasswordUpdateDTO passwordUpdateDTO) throws Exception {
//        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword()))
//            throw new Exception("Old password is wrong");
//
//        userService.updateUser(user)
//        userService.
//    }
}
