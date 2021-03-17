package com.example.demo.user.domain;


import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@NoArgsConstructor
public class UserBasicInfo {

    @Embedded
    private Username username; // = email

    @Embedded
    private Password password; // = passowrd

    public UserBasicInfo(Username username, Password password){
        this.username = username;
        this.password = password;
    }

    public String getPasswordInfo(){
        return this.password.getPassword();
    }

    public String getUsernameInfo(){
        return this.username.getUsername();
    }
}
