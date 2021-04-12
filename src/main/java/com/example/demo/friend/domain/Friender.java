package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friender extends BasicInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonId frienderId;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendships = new ArrayList<>();

    @Embedded
    private PersonName frienderName;

    private Friender(PersonId frienderId, PersonName frienderName){
        this.frienderId = frienderId;
        this.frienderName = frienderName;
    }


    public static Friender create(PersonId userId, PersonName userName){
        return new Friender(userId, userName);
    }

    @Override
    public Long getId(){
        return this.frienderId.getId();
    }

    @Override
    public String getName(){
        return this.frienderName.getName();
    }


}
