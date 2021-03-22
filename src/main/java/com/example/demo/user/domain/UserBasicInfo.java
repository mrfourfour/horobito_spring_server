package com.example.demo.user.domain;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBasicInfo {

    @Embedded
    private Username username; // = email

    @Embedded
    private Password password; // = passowrd

    public UserBasicInfo(Username username, Password password){
        this.username = username;
        this.password = password;
    }

    public String getPassword(){
        return this.password.getPassword();
    }

    public String getUsername(){
        return this.username.getUsername();
    }
}
