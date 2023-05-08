package com.oauth2.springbootoauth2sociallogin.service.oauth2;

import com.oauth2.springbootoauth2sociallogin.domain.AppUser;
import com.oauth2.springbootoauth2sociallogin.enums.LoginProvider;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Value
@NonFinal
public class AppUserService {

    PasswordEncoder passwordEncoder;

    DefaultOAuth2UserService oauth2Delegate = new DefaultOAuth2UserService();
    OidcUserService oidcDelegate = new OidcUserService();




    /**
     * Adapts oidc login to return AppUser instead of default OidcUser
     *
     * @return service that returns AppUser from request to the Oidc UserInfo Endpoint
     */
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {

        return userRequest -> {
            LoginProvider provider = getProvider(userRequest);
            OidcUser oidcUser = oidcDelegate.loadUser(userRequest);
            AppUser appUser = AppUser
                    .builder()
                    .provider(provider)
                    .username(oidcUser.getEmail())
                    .name(oidcUser.getFullName())
                    .email(oidcUser.getEmail())
                    .userId(oidcUser.getName())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .attributes(oidcUser.getAttributes())
                    .authorities(oidcUser.getAuthorities())
                    .build();

            return appUser;
        };
    }

    /**
     * Adapts oauth2 login to return AppUser instead of default OAauth2User
     *
     * @return service that returns AppUser from request to the Oauth2 user info
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            LoginProvider provider = getProvider(userRequest);
            OAuth2User oAuth2User = oauth2Delegate.loadUser(userRequest);
            AppUser appUser = AppUser
                    .builder()
                    .provider(provider)
                    .username(oAuth2User.getAttribute("login"))
                    .name(oAuth2User.getAttribute("login"))
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .userId(oAuth2User.getName())
                    .attributes(oAuth2User.getAttributes())
                    .authorities(oAuth2User.getAuthorities())
                    .build();


            return appUser;
        };
    }

    private LoginProvider getProvider(OAuth2UserRequest userRequest) {
        String githubProvider=LoginProvider.GITHUB.name();
        if (userRequest.getClientRegistration().getRegistrationId().toUpperCase().equals("GİTHUB")){
            return LoginProvider.valueOf(githubProvider);
        }
        return LoginProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
    }
}