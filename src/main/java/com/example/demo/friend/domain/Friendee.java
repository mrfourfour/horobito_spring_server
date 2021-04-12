package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friendee extends BasicInfo{


    @Embedded
    private PersonId friendeeId;

    @Embedded
    private PersonName friendeeName;

    private Friendee(PersonId friendeeId, PersonName friendeeName){
        this.friendeeId = friendeeId;
        this.friendeeName = friendeeName;
    }

    public static Friendee create(PersonId friendId, PersonName friendname) {
        return new Friendee(friendId, friendname);

    }
    @Override
    public Long getId(){
        return this.friendeeId.getId();
    }

    @Override
    public String getName(){
        return this.friendeeName.getName();
    }
}
