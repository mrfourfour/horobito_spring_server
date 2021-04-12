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
public class Friendee extends BasicInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_pk")
    private Long id;

    @Embedded
    private PersonId friendId;

    @Embedded
    private PersonName friendname;

    private Friendee(PersonId friendId, PersonName friendname){
        this.friendId = friendId;
        this.friendname = friendname;
    }

    public static Friendee create(PersonId friendId, PersonName friendname) {
        return new Friendee(friendId, friendname);

    }
    @Override
    public Long getId(){
        return this.friendId.getId();
    }

    @Override
    public String getName(){
        return this.friendname.getName();
    }
}
