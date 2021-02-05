package com.example.demo.login.service;

import com.example.demo.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //AuthenticationFilter는 생성한 UsernamePasswordToken을 AuthenticationManager에게 전달

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        UsernamePasswordAuthenticationToken authRequest;
            User user = new User(request.getParameter("userId"), request.getParameter("password"));
            authRequest = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    public void conveyToken(UsernamePasswordAuthenticationToken token, HttpServletResponse response){
        CustomLoginSuccessHandler customLoginSuccessHandler
                = new CustomLoginSuccessHandler();
        customLoginSuccessHandler.onAuthenticationSuccess(response, token);
    }

}
