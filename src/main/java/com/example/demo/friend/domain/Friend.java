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
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_pk")
    private Long id;

    @Embedded
    private Identfication friendId;

    @Embedded
    private Name friendname;

    private Friend(Identfication friendId, Name friendname){
        this.friendId = friendId;
        this.friendname = friendname;
    }

    public static Friend create(Identfication friendId, Name friendname) {
        return new Friend(friendId, friendname);

    }

    public Long getFriendId(){
        return this.friendId.getId();
    }

    public String getFriendName(){
        return this.friendname.getName();
    }
}
