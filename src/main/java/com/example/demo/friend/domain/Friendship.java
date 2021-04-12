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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Friender userInfo;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Friendee friend;

    @Column(name = "friend_state")
    private Boolean friendState;

    private Friendship(Friender userInfo, Friendee friend){
        this.userInfo = userInfo;
        this.friend = friend;
        this.friendState = false;
    }

    public static Friendship create(Friender user, Friendee friend) {
        return new Friendship(user, friend);
    }

    public Long getFriendId(){
        return this.friend.getId();
    }

    public void acceptFriendShip() {
        this.friendState = true;
    }

    public void deleteFriendShip() {
        this.friendState = false;
    }
}
