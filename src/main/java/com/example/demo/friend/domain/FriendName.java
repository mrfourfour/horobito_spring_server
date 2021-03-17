package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter(AccessLevel.PACKAGE)
@Embeddable
public class FriendName {

    @Column(name = "friend_name")
    private String friendName;

    public FriendName(String friendName) {
        this.friendName = friendName;
    }
}
