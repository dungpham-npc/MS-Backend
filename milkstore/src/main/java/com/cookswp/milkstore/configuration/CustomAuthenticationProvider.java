package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.exception.UserInvisibilityException;
import com.cookswp.milkstore.pojo.dtos.UserModel.CustomUserDetails;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserRegistrationDTO user = userService.getUserByEmail(email);

        try {
            if (user == null)
                throw new UsernameNotFoundException("User not found!");

            if (!passwordEncoder.matches(password, user.getPassword()))
                throw new BadCredentialsException("Invalid password!");

            if (userService.checkVisibilityStatusByEmail(email))
                throw new UserInvisibilityException("User might be prohibited or deleted from the system, please contact the administrator for further information!");

        } catch (UsernameNotFoundException | BadCredentialsException | UserInvisibilityException e) {
            System.out.println("Validation exceptions: " + e.getMessage());
            throw e;
        }

        CustomUserDetails userDetail = new CustomUserDetails(
                user.getUsername(),
                email,
                password,
                List.of(new SimpleGrantedAuthority(user.getRoleName())),
                null
        );
        return new UsernamePasswordAuthenticationToken(userDetail, password, userDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
