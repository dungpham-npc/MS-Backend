package com.cookswp.milkstore.api;

import com.cookswp.milkstore.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail() {
        String to = "dungpase183097@fpt.edu.vn"; //
        String subject = "Test Email from Spring Boot App";
        String text = "This is a test email sent from your Spring Boot application.";

        emailService.sendSimpleMessage(to, subject, text);

        return ResponseEntity.ok("Test email sent successfully!");
    }
}
