package com.oauth2.springbootoauth2sociallogin.service.user;

import com.oauth2.springbootoauth2sociallogin.domain.AppUser;
import com.oauth2.springbootoauth2sociallogin.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User createUser(AppUser appUser);
    List<User> getUsers();
}
