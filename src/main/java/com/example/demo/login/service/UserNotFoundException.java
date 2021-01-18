package com.example.demo.login.service;

import com.example.demo.user.domain.User;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String userId){
        super(userId + " NotFoundException");
    }
}
