package com.oauth2.springbootoauth2sociallogin.domain;

import com.oauth2.springbootoauth2sociallogin.enums.LoginProvider;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
public class AppUser implements  OidcUser {


    String username;
    String password;
    String email;
    String userId;
    String name;

    LoginProvider provider;

    Map<String, Object> attributes;
    Collection<? extends GrantedAuthority> authorities;


    @Override
    public String getName() {
        return Objects.nonNull(name) ? name : username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public Map<String, Object> getClaims() {
        return new HashMap<>();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", provider=" + provider +
                '}';
    }
}
