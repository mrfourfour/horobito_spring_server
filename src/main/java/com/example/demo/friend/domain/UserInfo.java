package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter(AccessLevel.PACKAGE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo extends BasicInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Identfication userId;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendships = new ArrayList<>();

    @Embedded
    private Name username;

    private UserInfo(Identfication userId, Name username){
        this.userId = userId;
        this.username = username;
    }


    public static UserInfo create(Identfication userId, Name userName){
        return new UserInfo(userId, userName);
    }

    @Override
    public Long getId(){
        return this.userId.getId();
    }

    @Override
    public String getName(){
        return this.username.getName();
    }


}
