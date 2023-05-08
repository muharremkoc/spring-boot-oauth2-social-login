package com.oauth2.springbootoauth2sociallogin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@Slf4j
public class SecurityConfig{


 /*   @Autowired
    private GithubLoginServiceImpl githubLoginService;*/


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler,
                                            OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler) throws Exception {
        return http
                .oauth2Login(oc -> oc

                        .defaultSuccessUrl("http://localhost:1111/getLogin")
                        .userInfoEndpoint(ui -> ui
                                .userService(oauth2LoginHandler)
                                .oidcUserService(oidcLoginHandler)))
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/","/users").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("success: {}", event.getAuthentication());
        };
    }
}
