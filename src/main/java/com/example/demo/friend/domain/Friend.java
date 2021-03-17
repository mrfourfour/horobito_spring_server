package com.example.demo.friend.domain;


import com.example.demo.friend.service.FriendDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_pk")
    private Long id;

    @Embedded
    private FriendId friendId;

    @Embedded
    private FriendName friendname;

    public Friend(FriendId friendId, FriendName friendname){
        this.friendId = friendId;
        this.friendname = friendname;
    }

    public static Friend createFriend(FriendId friendId, FriendName friendname) {
        return new Friend(friendId, friendname);

    }
}
