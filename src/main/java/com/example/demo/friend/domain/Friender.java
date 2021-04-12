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
public class Friender extends BasicInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonId userId;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendships = new ArrayList<>();

    @Embedded
    private PersonName username;

    private Friender(PersonId userId, PersonName username){
        this.userId = userId;
        this.username = username;
    }


    public static Friender create(PersonId userId, PersonName userName){
        return new Friender(userId, userName);
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
