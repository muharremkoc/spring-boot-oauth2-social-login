package com.oauth2.springbootoauth2sociallogin.repository;

import com.oauth2.springbootoauth2sociallogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
