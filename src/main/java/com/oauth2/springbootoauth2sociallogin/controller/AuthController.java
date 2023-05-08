package com.oauth2.springbootoauth2sociallogin.controller;

import com.oauth2.springbootoauth2sociallogin.domain.AppUser;

import com.oauth2.springbootoauth2sociallogin.domain.User;
import com.oauth2.springbootoauth2sociallogin.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "Welcome To HomePage";
    }

    @GetMapping("/getLogin")
    public User getLogin(){


        return userService.createUser(AppUser.builder().build());
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
