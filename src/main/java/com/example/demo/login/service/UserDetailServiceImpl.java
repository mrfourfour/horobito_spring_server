package com.example.demo.login.service;


import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String userId) {
        return userRepository.findByUserId(userId).map(u -> new MyUserDetails(u,  Collections.singleton(
                new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(()-> new UserNotFoundException(userId));
    }
}
