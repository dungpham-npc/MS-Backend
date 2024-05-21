package com.cookswp.milkstore.service;

import com.cookswp.milkstore.model.User;
import com.cookswp.milkstore.model.UserDTO;
import com.cookswp.milkstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository userRepository){
        this.mapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserDTO getUser(String username){
        User user = userRepository.findByUsername(username);

        UserDTO userDto = mapper.map(user, UserDTO.class);

        return userDto;
    }
}
