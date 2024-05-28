package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.model.UserRegistrationDTO;
import com.cookswp.milkstore.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();

            UserRegistrationDTO user = userService.getUserByEmail(email);

            if (user != null) {
                DefaultOAuth2User newUser = new DefaultOAuth2User(
                        List.of(new SimpleGrantedAuthority(user.getRoleName())),
                        attributes,
                        "sub");

                Authentication securityAuth = new OAuth2AuthenticationToken(
                        newUser,
                        List.of(new SimpleGrantedAuthority(user.getRoleName())),
                        token.getAuthorizedClientRegistrationId());

                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            } else {
                UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
                userRegistrationDTO.setEmailAddress(email);
                userRegistrationDTO.setUsername(name);
                userService.registerUser(userRegistrationDTO);
                DefaultOAuth2User newUser = new DefaultOAuth2User(
                        List.of(new SimpleGrantedAuthority(userRegistrationDTO.getRoleName())),
                        attributes,
                        "sub");
                Authentication securityAuth = new OAuth2AuthenticationToken(
                        newUser,
                        List.of(new SimpleGrantedAuthority(userRegistrationDTO.getRoleName())),
                        "sub");
                SecurityContextHolder.getContext().setAuthentication(securityAuth);

                String additionalInfoUrl = "http://localhost:8080/register/complete-registration?email=" + email;
                response.sendRedirect(additionalInfoUrl);
                return;
            }



        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:8080/test");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
