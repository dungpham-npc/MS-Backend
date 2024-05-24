//package com.cookswp.milkstore.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/user").permitAll();
//                    auth.anyRequest().authenticated();
//        })
//                .oauth2Login(oauth2 ->{
//                    oauth2.successHandler(oAuth2LoginSuccessHandler);
//                })
//                .build();
//    }
//}
