<<<<<<< HEAD
//package com.cookswp.milkstore.service;
//
//import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
//import com.cookswp.milkstore.pojo.dtos.UserModel.User;
//import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//@Transactional
//public class UserService {
//
//    private final ModelMapper mapper;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleService roleService;
//
//    @Autowired
//    public UserService(ModelMapper modelMapper,
//                       PasswordEncoder passwordEncoder,
//                       UserRepository userRepository,
//                       RoleService roleService
//                       ){
//        this.mapper = modelMapper;
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//        this.roleService =roleService;
//    }
//
//    public UserDTO getUserByUsername(String username){
//        User user = userRepository.findByUsername(username);
//
//        UserDTO userDto = mapper.map(user, UserDTO.class);
//
//        return userDto;
//    }
//
//    public UserRegistrationDTO getUserByEmail(String email){
//        User user = userRepository.findByEmailAddress(email);
//        if (user != null)
//            return mapper.map(user, UserRegistrationDTO.class);
//        else
//            return null;
//    }
//
//    public void registerUser(UserRegistrationDTO userRegistrationDTO){
//        String password = userRegistrationDTO.getPassword();
//        if (password != null)
//            userRegistrationDTO.setPassword(passwordEncoder.encode(password));
//
//        User user = mapper.map(userRegistrationDTO, User.class);
//
//        user.setRole(roleService.getRoleByRoleName("CUSTOMER"));
//        user.setVisibilityStatus(true);
//        userRepository.save(user);
//    }
//
//    public void registerStaff(UserRegistrationDTO userRegistrationDTO){
//        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
//        User user = mapper.map(userRegistrationDTO, User.class);
//
//        user.setRole(roleService.getRoleByRoleName(userRegistrationDTO.getRoleName()));
//        user.setVisibilityStatus(true);
//        userRepository.save(user);
//    }
//
//    public boolean checkEmailExistence(String email){
//        return userRepository.findByEmailAddress(email) != null;
//    }
//
//    public boolean checkPhoneNumberExistence(String phone){
//        return userRepository.findByPhoneNumber(phone) != null;
//    }
//
//    public List<UserRegistrationDTO> getInternalUserList(){
//        List<User> userEntitiesList = userRepository.findAllStaffs();
//        List<UserRegistrationDTO> userList = new ArrayList<>();
//        if (userEntitiesList.isEmpty()) return Collections.emptyList();
//
//        for (User user : userEntitiesList) {
//            userList.add(mapper.map(user, UserRegistrationDTO.class));
//        }
//
//        return userList;
//    }
//
//    public List<UserDTO> getMemberUserList(){
//        List<User> userEntitiesList = userRepository.findAllMembers();
//        List<UserDTO> userList = new ArrayList<>();
//        if (userEntitiesList.isEmpty()) return Collections.emptyList();
//
//        for (User user : userEntitiesList) {
//            userList.add(mapper.map(user, UserDTO.class));
//        }
//
//        return userList;
//    }
//
//    public void updateUser(int id, UserRegistrationDTO userRegistrationDTO){
//        userRepository.updateUser(id,
//                userRegistrationDTO.getEmailAddress(),
//                userRegistrationDTO.getPhoneNumber(),
//                passwordEncoder.encode(userRegistrationDTO.getPassword()),
//                userRegistrationDTO.getUsername());
//    }
//    @Transactional
//    public void deleteUser(int id){
//        userRepository.deleteUser(id);
//    }
//
//
//}
=======
package com.cookswp.milkstore.service;

import com.cookswp.milkstore.pojo.dtos.UserModel.CustomUserDetails;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
import com.cookswp.milkstore.repository.UserRepository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@EnableWebSecurity
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ModelMapper mapper;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       RoleService roleService,
                       ModelMapper mapper
                       ){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmailAddress(email);
    }

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.getRoleByRoleName("CUSTOMER"));
        user.setVisibilityStatus(true);
        return userRepository.save(user);
    }

    public User registerStaff(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.getRoleByRoleName(user.getRoleName()));
        user.setVisibilityStatus(true);
        return userRepository.save(user);
    }

    public boolean checkEmailExistence(String email){
        return userRepository.findByEmailAddress(email) != null;
    }

    public boolean checkPhoneNumberExistence(String phone){
        return userRepository.findByPhoneNumber(phone) != null;
    }

    public List<User> getInternalUserList(){
        return userRepository.findAllStaffs();
    }

    public List<User> getMemberUserList(){
        return userRepository.findAllMembers();
    }

    public int updateUser(int id, User user){
        return userRepository.updateUser(id,
                user.getEmailAddress(),
                user.getPhoneNumber(),
                passwordEncoder.encode(user.getPassword()),
                user.getUsername());
    }
    @Transactional
    public void deleteUser(int id){
        userRepository.deleteUser(id);
    }

    public boolean checkVisibilityStatusByEmail(String email){
        return userRepository.isVisible(email);
    }

    public User getCurrentUser(){
        return userRepository.findByEmailAddress(
                ((CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
                )
                        .getName());
    }




}
>>>>>>> main
