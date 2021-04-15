package com.example.demo.security.service;


import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(Username){
        return null;
    }
}
