package com.example.demo.user.service;

import lombok.Value;

@Value
public class UserDto {
    Long userId;
    String username;

    private UserDto(Long userId, String username){
        this.userId = userId;
        this.username = username;
    }

    public static UserDto create(Long userId, String username){
        return new UserDto(userId, username);
    }

}
