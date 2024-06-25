package com.cookswp.milkstore.configuration;


import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.service.account.AccountService;
import com.cookswp.milkstore.service.user.UserService;
//import com.cookswp.milkstore.utils.JwtUtils;
import com.cookswp.milkstore.utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomFormLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private final JwtUtils jwtUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"token\": \"" + jwt + "\"}");
        response.getWriter().flush();

//        this.setAlwaysUseDefaultTargetUrl(true);
//        this.setDefaultTargetUrl("http://localhost:8080/test");
//        super.onAuthenticationSuccess(request, response, authentication);
    }


}
