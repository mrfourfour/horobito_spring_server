package com.example.demo.user.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Embeddable
// joinColumns = @JoinColumn(name = "user_id") : 외래키 의미
public class IsFriend {
    @MapKeyColumn(name = "friend_id")
    @Column(name = "is_friend")
    @CollectionTable(name = "user", joinColumns = @JoinColumn(name = "user_id"))
    private HashMap<Long, Integer > friendList  = new HashMap<>();

}
