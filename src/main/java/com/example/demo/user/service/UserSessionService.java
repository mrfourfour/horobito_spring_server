package com.example.demo.user.service;


import com.example.demo.security.service.UserSessionUtils;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.SecurityUser;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserRepository userRepository;


    public User getLoggeddUser() throws AccessDeniedException {
        SecurityUser securityUser = UserSessionUtils.getSessionUser();
        Long userId = securityUser.getId();

        return userRepository.findUserById(userId);
    }
}
