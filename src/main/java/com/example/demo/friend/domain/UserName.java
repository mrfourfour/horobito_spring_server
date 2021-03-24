package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserName {

    @Column(name = "user_name")
    private String username;


    private UserName(String username){
        this.username = username;
    }

    public static UserName create(String userName){
        return new UserName(userName);
    }
}
