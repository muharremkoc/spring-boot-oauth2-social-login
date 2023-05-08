package com.oauth2.springbootoauth2sociallogin.service.user;

import com.oauth2.springbootoauth2sociallogin.domain.AppUser;
import com.oauth2.springbootoauth2sociallogin.domain.User;
import com.oauth2.springbootoauth2sociallogin.enums.LoginProvider;
import com.oauth2.springbootoauth2sociallogin.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(AppUser appUser) {
        appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User existUser =userRepository.findByEmail(appUser.getUsername());
        if (existUser!=null) return existUser;
        User user = new User();
        user.setLoginProvider(appUser.getProvider());
        if (user.getLoginProvider().equals(LoginProvider.GITHUB)) user.setEmail(appUser.getUsername());
        else if (user.getLoginProvider().equals(LoginProvider.GOOGLE)) user.setEmail(appUser.getEmail());
         return userRepository.save(new User(user.getEmail(),user.getLoginProvider()));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
