package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter(AccessLevel.PACKAGE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendName {

    @Column(name = "friend_name")
    private String friendName;

    private FriendName(String friendName) {
        this.friendName = friendName;
    }

    public static FriendName create(String friendName){
        return new FriendName(friendName);
    }
}
