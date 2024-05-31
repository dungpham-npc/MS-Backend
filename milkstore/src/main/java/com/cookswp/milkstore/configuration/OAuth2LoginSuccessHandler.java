package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.pojo.dtos.UserModel.CustomUserDetails;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String email = attributes.getOrDefault("email", "").toString();
        String name = attributes.getOrDefault("name", "").toString();

        UserRegistrationDTO user = userService.getUserByEmail(email);

        if (user != null) {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(user, attributes));

        } else {

            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
            userRegistrationDTO.setEmailAddress(email);
            userRegistrationDTO.setUsername(name);
            userRegistrationDTO.setRoleName("CUSTOMER");
            userService.registerUser(userRegistrationDTO);

            SecurityContextHolder.getContext().setAuthentication(getAuthentication(userRegistrationDTO, attributes));
            String redirectUrl = "http://localhost:3000/?email="
                    + URLEncoder.encode(email, StandardCharsets.UTF_8) + "&username="
                    + URLEncoder.encode(name, StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
            return;
        }



        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:8080/test");
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private static Authentication getAuthentication(UserRegistrationDTO user, Map<String, Object> attributes) {
        CustomUserDetails userDetail = new CustomUserDetails(
                user.getUsername(),
                user.getEmailAddress(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRoleName())),
                attributes);

        return new UsernamePasswordAuthenticationToken(
                userDetail,
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRoleName()))
        );
    }

}
