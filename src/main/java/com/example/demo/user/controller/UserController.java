package com.example.demo.user.controller;


import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller

public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;



    @PostMapping("/")
    public String login(){

        return "aaa";

    }
}
