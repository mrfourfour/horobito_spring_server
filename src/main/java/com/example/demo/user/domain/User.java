package com.example.demo.user.domain;

import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // = code

    @Column(nullable = false, unique = true)
    private String userId; // = email

    @Column(nullable = false)
    private String password; // = passowrd




    private HashMap<Long, Boolean> isFriend  = new HashMap<>();

    public void setFriend(User user) {
        isFriend.put(user.getId(), false);
    }

    public Boolean findFriend(User friendUser) {
        return isFriend.get(friendUser.getId());
    }

    public void agreeFriend(User friendUser) {
        isFriend.put(friendUser.getId(), true);
    }

    public void deleteFriend(User user) {
        isFriend.put(user.getId(), null);
    }


    public List<Long> findRequest() {
        Set<Long> keys = isFriend.keySet();
        List<Long> friendIds = new ArrayList<>();

        for (Long friendId : keys){
            if(isFriend.get(friendId)==false){
                friendIds.add(friendId);
            }
        }
        return friendIds;
    }
}
