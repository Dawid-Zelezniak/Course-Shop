package com.zelezniak.project.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createNewUser(UserData userData);
}
    