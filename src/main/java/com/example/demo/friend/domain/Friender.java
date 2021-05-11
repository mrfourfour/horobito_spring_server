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



    @Embedded
    @AttributeOverride(
            name = "id", column = @Column(name = "friender_id")
    )
    private PersonId frienderId;

    @Embedded
    @AttributeOverride(
            name = "name", column = @Column(name = "friender_name")
    )
    private PersonName frienderName;

    private Friender(PersonId frienderId, PersonName frienderName){
        this.frienderId = frienderId;
        this.frienderName = frienderName;
    }


    public static Friender create(PersonId userId, PersonName userName){
        return new Friender(userId, userName);
    }

    @Override
    @Transient
    public Long getId(){
        return this.frienderId.getId();
    }

    @Override
    @Transient
    public String getName(){
        return this.frienderName.getName();
    }


}
