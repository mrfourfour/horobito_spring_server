package com.example.demo.user.controller;


import com.example.demo.login.service.CustomAuthenticationFilter;
import com.example.demo.user.service.SignUp;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

@RequiredArgsConstructor
@Controller

public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;




    @PostMapping("/account/signup")
    public String signUp(@RequestBody SignUp signUp){
        return userService.signUp(signUp);
    }


    @PostMapping("/account/login")
    public HttpServletResponse login(HttpServletRequest request,
                                     HttpServletResponse response){
        return userService.signIn(request, response);


    }
}
