package com.example.demo.user.application;


import lombok.Value;

@Value
public class LoginRequest {
    String username;
    String password;

    public LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
}
