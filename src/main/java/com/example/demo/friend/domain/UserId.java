package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId {

    @Column(name = "user_id")
    private Long userId;

    private UserId(Long userId){
        this.userId = userId;
    }

    public static UserId create(Long userId){
        return new UserId(userId);
    }


}
