package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.pojo.dtos.UserModel.CustomUserDetails;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.service.RoleService;
import com.cookswp.milkstore.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final UserService userService;
    @Autowired
    private final RoleService roleService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String email = attributes.getOrDefault("email", "").toString();
        String name = attributes.getOrDefault("name", "").toString();

        User user = userService.getUserByEmail(email);

        if (user != null) {

            if (!userService.checkVisibilityStatusByEmail(email)) {
                response.sendRedirect("http://localhost:3000/login-error");
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(user, attributes));

        } else {

            User newUser = new User();
            newUser.setEmailAddress(email);
            newUser.setUsername(name);
            newUser.setRole(roleService.getRoleByRoleName("CUSTOMER"));
            userService.registerUser(newUser);

            SecurityContextHolder.getContext().setAuthentication(getAuthentication(newUser, attributes));
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

    private static Authentication getAuthentication(User user, Map<String, Object> attributes) {
        CustomUserDetails userDetail = new CustomUserDetails(
                user.getPhoneNumber(),
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
