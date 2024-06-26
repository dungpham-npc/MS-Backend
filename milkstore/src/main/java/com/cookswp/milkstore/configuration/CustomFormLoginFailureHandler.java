package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.exception.UserInvisibilityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomFormLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED; // Default status code

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "User not found!";
            statusCode = HttpServletResponse.SC_NOT_FOUND;
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password!";
        } else if (exception != null) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "Login failed!";
        }

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"message\": \"" + errorMessage + "\"}");
        response.getWriter().flush();
    }
}
