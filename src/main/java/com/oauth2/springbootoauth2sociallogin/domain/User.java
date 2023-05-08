package com.oauth2.springbootoauth2sociallogin.domain;

import com.oauth2.springbootoauth2sociallogin.enums.LoginProvider;
import jakarta.persistence.*;
import lombok.Builder;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String email;
    private LoginProvider loginProvider;

    public User() {
    }

    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String email,LoginProvider loginProvider) {
        this.email = email;
        this.loginProvider = loginProvider;
    }

    public User(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginProvider getLoginProvider() {
        return loginProvider;
    }

    public void setLoginProvider(LoginProvider loginProvider) {
        this.loginProvider = loginProvider;
    }
}
