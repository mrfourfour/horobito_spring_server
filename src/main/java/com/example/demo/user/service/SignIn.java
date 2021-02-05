package com.example.demo.user.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor
public class SignIn {
    String userId;
    String password;
}
