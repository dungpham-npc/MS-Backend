package com.cookswp.milkstore.service;

import com.cookswp.milkstore.model.Role;
import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.model.User;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.repository.RoleRepository;
import com.cookswp.milkstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       RoleService roleService
                       ){
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService =roleService;
    }

    public UserDTO getUserByUsername(String username){
        User user = userRepository.findByUsername(username);

        UserDTO userDto = mapper.map(user, UserDTO.class);

        return userDto;
    }

    public UserRegistrationDTO getUserByEmail(String email){
        User user = userRepository.findByEmailAddress(email);
        if (user != null)
            return mapper.map(user, UserRegistrationDTO.class);
        else
            return null;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        String password = userRegistrationDTO.getPassword();
        if (password != null)
            userRegistrationDTO.setPassword(passwordEncoder.encode(password));

        userRegistrationDTO.setRoleName("CUSTOMER");

        User user = mapper.map(userRegistrationDTO, User.class);

        Role role = roleService.getRoleByRoleName(userRegistrationDTO.getRoleName());

        user.setRole(role);

        userRepository.save(user);
    }

    public boolean loginUser(UserRegistrationDTO userRegistrationDTO){
        User user = userRepository.findByEmailAddress(userRegistrationDTO.getEmailAddress());

        if (user == null) return false;

        return passwordEncoder.matches(userRegistrationDTO.getPassword(), user.getPassword());
    }


}
