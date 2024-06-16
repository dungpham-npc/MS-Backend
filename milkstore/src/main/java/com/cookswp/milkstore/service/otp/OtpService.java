package com.cookswp.milkstore.service.otp;

import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.user.UserRepository;
import com.cookswp.milkstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OtpService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public OtpService(UserService userService,
                      UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }
    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }

    public void assignOtpAndCreatedDateToUserByEmail(String email){
        User user = userRepository.findByEmailAddress(email);
        user.setOtpCode(generateOtp());
        user.setOtpCreatedAt(LocalDateTime.now().format(dateTimeFormatter));
    }
}
