package com.example.demo.security.service;


import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.SecurityUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Username name = Username.create(username);

        Optional<User> user1 = userRepository
                .findUserByUserBasicInfo_Username(name)
                .map(user -> new SecurityUser(
                        user.getId(),
                        user.getUserBasicInfo().getUsername(),
                        user.g
                ))
        return null;
    }
}
