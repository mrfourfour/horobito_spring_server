package com.example.demo.user.domain;


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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long frinedId;

    private Boolean isFriend;

    private Friend(Long frinedId){
        this.frinedId = frinedId;
    }


    public static Friend makeFriend(Long friendId) {
        return new Friend(friendId);
    }
}
