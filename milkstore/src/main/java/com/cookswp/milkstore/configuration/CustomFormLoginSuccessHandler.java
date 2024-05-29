//package com.cookswp.milkstore.configuration;
//
//
//import com.cookswp.milkstore.service.UserService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomFormLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public CustomFormLoginSuccessHandler(UserService userService, PasswordEncoder passwordEncoder){
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        this.setAlwaysUseDefaultTargetUrl(true);
//        this.setDefaultTargetUrl("http://localhost:8080/test");
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//
//
//}
