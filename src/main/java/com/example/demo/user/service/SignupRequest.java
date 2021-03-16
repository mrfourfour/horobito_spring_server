package com.example.demo.user.service;


import lombok.Value;

@Value
public class SignupRequest {
    String username;
    String password;

    String authorities;

}
