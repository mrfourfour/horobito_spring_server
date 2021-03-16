package com.example.demo.user.controller;



import com.example.demo.user.application.LoginRequest;
import com.example.demo.user.service.SignupRequest;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.springframework.web.bind.annotation.*;

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
    public void Login(@RequestBody SignupParameter parameter){
        LoginRequest loginRequest = new LoginRequest(
            parameter.username,
            parameter.password
        );
        userService.login(loginRequest);
    }


    @Value
    public static class SignupParameter {
        String username;
        String password;
    }


}
