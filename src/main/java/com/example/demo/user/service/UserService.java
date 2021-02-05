package com.example.demo.user.service;




import com.example.demo.login.service.CustomAuthenticationFilter;

import com.example.demo.login.service.WebSecurityConfig;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class UserService  {

    private final CustomAuthenticationFilter authenticationFilter;
    private final UserRepository userRepository;


    public String signUp(SignUp signUp) {
        User user = User.createUser(signUp);
        if(!userRepository.existsByUserId(user.getUserId())){
            userRepository.save(user);
            return "201 Created";
        }else {
            return "400 Request";
        }


    }

    public HttpServletResponse signIn(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationFilter.attemptAuthentication(request, response);
        UsernamePasswordAuthenticationToken token
                = (UsernamePasswordAuthenticationToken) authentication;
        authenticationFilter.conveyToken(token, response);
        return response;
    }
}
