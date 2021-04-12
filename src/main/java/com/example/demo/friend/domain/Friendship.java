package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "friendship")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Friender friender;

    @Embedded
    private Friendee friendee;

    @Column(name = "friend_state")
    private Boolean friendState;

    private Friendship(Friender friender, Friendee friendee){
        this.friender = friender;
        this.friendee = friendee;
        this.friendState = false;
    }

    public static Friendship create(Friender user, Friendee friend) {
        return new Friendship(user, friend);
    }

    public Long getFriendeeId(){
        return this.friendee.getId();
    }

    public void acceptFriendShip() {
        this.friendState = true;
    }

    public void deleteFriendShip() {
        this.friendState = false;
    }
}
