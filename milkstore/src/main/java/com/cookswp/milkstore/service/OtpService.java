package com.cookswp.milkstore.service;

import com.cookswp.milkstore.pojo.entities.TemporaryUser;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.UserRepository.TemporaryUserRepository;
import com.cookswp.milkstore.repository.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class OtpService {
    private final TemporaryUserRepository temporaryUserRepository;
    private final UserService userService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OtpService(UserService userService,
                      TemporaryUserRepository temporaryUserRepository,
                      EmailService emailService,
                      PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.temporaryUserRepository =temporaryUserRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }

//    public void sendForgotPasswordOtpByEmail(String email){
//        User user = userService.getUserByEmail(email);
//        user.setOtpCode(generateOtp());
//        user.setOtpCreatedAt(LocalDateTime.now().format(dateTimeFormatter));
//    }

    public void sendRegistrationOtpByEmail(String email) {
        if (temporaryUserRepository.findByEmailAddress(email) != null)
            temporaryUserRepository.deleteByEmailAddress(email);
        TemporaryUser newTempUser = new TemporaryUser();
        newTempUser.setEmailAddress(email);
        newTempUser.setOtpCode(passwordEncoder.encode(generateOtp()));
        newTempUser.setOtpCreatedAt(LocalDateTime.now());
        newTempUser.setOtpExpiredAt(LocalDateTime.now().plusMinutes(10));
        temporaryUserRepository.save(newTempUser);
        emailService.sendMessage(email,
                "OTP Verification for Milk Store",
                "Your OTP code is: " + newTempUser.getOtpCode());
    }

    public boolean isOtpValid(String email, String inputOtpCode){
        TemporaryUser user = temporaryUserRepository.findByEmailAddress(email);
        return user != null &&
                passwordEncoder.matches(inputOtpCode, user.getOtpCode()) &&
                !LocalDateTime.now().isAfter(user.getOtpExpiredAt());
    }

    public void deleteTempUser(String email){
        temporaryUserRepository.deleteByEmailAddress(email);
    }
}
