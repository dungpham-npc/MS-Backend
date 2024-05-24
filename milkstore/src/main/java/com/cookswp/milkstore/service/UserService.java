package com.cookswp.milkstore.service;

import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.model.User;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository
                       ){
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDTO getUser(String username){
        User user = userRepository.findByUsername(username);

        UserDTO userDto = mapper.map(user, UserDTO.class);

        return userDto;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        User user = mapper.map(userRegistrationDTO, User.class);
        user.setRoleId(1);

        userRepository.save(user);
    }

    public boolean loginUser(UserRegistrationDTO userRegistrationDTO){
        User user = userRepository.findByEmailAddress(userRegistrationDTO.getEmailAddress());

        if (user == null) return false;

        return passwordEncoder.matches(userRegistrationDTO.getPassword(), user.getPassword());
    }


}
