//package com.cookswp.milkstore.configuration;
//
//import com.cookswp.milkstore.service.UserService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    private final UserService userService;
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//
////        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
////        if("github".equals(token.getAuthorizedClientRegistrationId())){
////            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
////            Map<String, Object> attributes = principal.getAttributes();
////            String email = attributes.getOrDefault("email", "").toString();
////            String name = attributes.getOrDefault("name", "").toString();
////
////        }
//
//        this.setAlwaysUseDefaultTargetUrl(true);
//        this.setDefaultTargetUrl("http://localhost:8080/user/customer");
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//}
