package com.example.demo.friend.domain;


import com.example.demo.user.domain.User;
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
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Friend friend;

    private Boolean friendState;

    public Friendship(User user, Friend friend){
        this.user = user;
        this.friend = friend;
        this.friendState = false;
    }

    public static Friendship create(User user, Friend friend) {
        return new Friendship(user, friend);
    }

    public void acceptFriendShip() {
        this.friendState = true;
    }
}
