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
    @AttributeOverride(
            name = "id", column = @Column(name = "friendee_id")
    )
    private PersonId friendeeId;

    @Embedded
    @AttributeOverride(
            name = "name", column = @Column(name = "friendee_name")
    )
    private PersonName friendeeName;

    private Friendee(PersonId friendeeId, PersonName friendeeName){
        this.friendeeId = friendeeId;
        this.friendeeName = friendeeName;
    }

    public static Friendee create(PersonId friendId, PersonName friendname) {
        return new Friendee(friendId, friendname);

    }
    @Override
    @Transient
    public Long getId(){
        return this.friendeeId.getId();
    }

    @Override
    @Transient
    public String getName(){
        return this.friendeeName.getName();
    }
}
