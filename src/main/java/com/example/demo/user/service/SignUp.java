package com.example.demo.user.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor
public class SignUp {
    String userId;
    String password;
}
