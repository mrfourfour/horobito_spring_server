package com.example.demo.friend.domain;


import com.example.demo.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "friend")
@Getter
@Setter
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long frinedId;

    @Embedded
    private Owner owner;

    private String friendname;

    private Boolean isFriend;



    public Friend(User friendUser){
        this.frinedId = friendUser.getId();
        this.friendname = friendUser.getUserId();
        this.isFriend = false;
    }


    public void agreeFriend() {
        this.isFriend = true;
    }
}
