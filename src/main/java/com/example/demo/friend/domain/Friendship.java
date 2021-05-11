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
    @Enumerated(EnumType.STRING)
    private FriendShipState friendState;

    private Friendship(Friender friender, Friendee friendee){
        this.friender = friender;
        this.friendee = friendee;
        this.friendState = FriendShipState.REQUESTED;
    }

    public static Friendship create(Friender user, Friendee friend) {
        return new Friendship(user, friend);
    }

    @Transient
    public Long getFriendeeId(){
        return this.friendee.getId();
    }

    public void acceptFriendShip() {
        this.friendState = FriendShipState.ACCEPT;
    }

    public void requestFriendShip(){
        this.friendState = FriendShipState.REQUEST;
    }

    public void deleteFriendShip() {
        this.friendState = FriendShipState.DELETED;
    }
}
