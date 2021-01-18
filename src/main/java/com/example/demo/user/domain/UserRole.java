package com.example.demo.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER");

    private String value;
}
