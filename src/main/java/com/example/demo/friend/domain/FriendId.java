package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PACKAGE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendId {

    @Column(name = "Friend_id")
    private Long friendId;

    private FriendId(Long friendId) {
        this.friendId = friendId;
    }

    public static FriendId create(Long friendId){
        return new FriendId(friendId);
    }
}
