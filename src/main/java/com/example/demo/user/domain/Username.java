package com.example.demo.user.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {

    @Column(name = "user_name", unique = true)
    private String username;

    private Username(String username){
        this.username = username;
    }

    public static Username create(String username) {
        return new Username(username);
    }
}
