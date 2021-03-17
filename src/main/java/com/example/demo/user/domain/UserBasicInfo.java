package com.example.demo.user.domain;


import lombok.Value;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
public class UserBasicInfo {

    @Embedded
    private Username username; // = email

    @Embedded
    private Password password; // = passowrd

    public String getPasswordInfo(){
        return this.password.getPassword();
    }

    public String getUsernameInfo(){
        return this.username.getUsername();
    }
}
