package com.cookswp.milkstore.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired
    private final CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private final CustomFormLoginSuccessHandler customFormLoginSuccessHandler;
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/register", "/login", "/oauth2/**").permitAll();
                    auth.anyRequest().authenticated();
        })
                .formLogin(form -> {
                    form.successHandler(customFormLoginSuccessHandler); // Use custom form login success handler
                })
                .oauth2Login(oauth2 ->{
                    oauth2.successHandler(oAuth2LoginSuccessHandler);
                })
                .build();
    }
}
