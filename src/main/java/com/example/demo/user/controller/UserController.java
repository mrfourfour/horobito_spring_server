package com.example.demo.user.controller;



import com.example.demo.user.service.LoginRequest;
import com.example.demo.user.service.SignupRequest;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;


import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



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
    public void Login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws ServletException, IOException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

//        UsernamePasswordAuthenticationToken token
//                = new UsernamePasswordAuthenticationToken(username, password);
//        SecurityContextHolder.ge
    }

    @Value
    public static class SignupParameter {
        String username;
        String password;
    }


}
