package com.example.demo.login.service;


import com.example.demo.user.domain.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;


@AllArgsConstructor
@Service("userDetailsService")
//아이디를 기반으로 데이터를 조회하는 역할
//조회한 결과를 CustomAuthenticaionProvider로 반환
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    @Override // userdetail 반환하는 곳...?
    public MyUserDetails loadUserByUsername(String userId) {
        return userRepository.findUserByUserId(userId).map(u -> new MyUserDetails(u, Collections.singleton(
                new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(()
                -> new UserNotFoundException(userId));
    }

}
