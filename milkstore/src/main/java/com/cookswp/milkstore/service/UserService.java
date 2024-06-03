package com.cookswp.milkstore.service;

import com.cookswp.milkstore.pojo.dtos.UserModel.CustomUserDetails;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.UserRepository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        if (user.getPassword() != null)
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

    public int updateUserBasicInformation(int id, User user){
        return userRepository.updateUser(id,
                user.getPhoneNumber(),
                user.getUsername());
    }

    public int updateUserPassword(int id, String newPassword){
        return userRepository.updateUserPassword(id, passwordEncoder.encode(newPassword));
    }

    public void deleteUser(int id){
        userRepository.deleteUser(id);
    }

    public boolean checkVisibilityStatusByEmail(String email){
        return userRepository.isVisible(email);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return Optional.empty();
        }

        return Optional.ofNullable(userRepository.findByEmailAddress(userDetails.getName()));
    }

    public void clearOtpInformationByEmail(String email){
        User user = userRepository.findByEmailAddress(email);
        user.setOtpCode(null);
        user.setOtpCreatedAt(null);
        user.setOtpExpiredAt(null);
    }


}
