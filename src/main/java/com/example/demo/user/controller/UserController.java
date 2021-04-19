package com.example.demo.user.controller;



import com.example.demo.security.service.UserDetailsService;
import com.example.demo.user.service.LoginRequest;
import com.example.demo.user.service.SignupRequest;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.springframework.web.bind.annotation.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;



    @PostMapping("/signup")
    public void signupWithNormalAccount(@RequestBody SignupParameter parameter){
        SignupRequest signupRequest = new SignupRequest(
                parameter.username,
                parameter.password,
                "ROLE_USER"
        );

        userService.signup(signupRequest);
    }

    @PostMapping("/signup/admin")
    public void signupWithAdminAccount(@RequestBody SignupParameter parameter){
        SignupRequest signupRequest = new SignupRequest(
                parameter.username,
                parameter.password,
                "ROLE_ADMIN"
        );

        userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.login(request, response);
    }

    @Value
    public static class SignupParameter {
        String username;
        String password;
    }


}
