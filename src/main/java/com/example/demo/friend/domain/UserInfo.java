package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PACKAGE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserId userId;

    @Embedded
    private UserName userName;

    private UserInfo(UserId userId, UserName userName){
        this.userId = userId;
        this.userName = userName;
    }


    public static UserInfo create(UserId userId, UserName userName){
        return new UserInfo(userId, userName);
    }


}
