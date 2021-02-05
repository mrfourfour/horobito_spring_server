package com.example.demo.user.domain;

import com.example.demo.login.service.UserRole;
import com.example.demo.user.service.SignUp;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // = code

    @Column(nullable = false, unique = true)
    private String userId; // = email

    @Column(nullable = false)
    private String password; // = passowrd

    @Embedded
            // 수정해야 할 부분
            //  user- friend 로 해야 하나
            // 아니면 user - friendlist 관계로 해야 하나...
    IsFriend isFriend;


    @Setter
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;



    public static User createUser(SignUp signUp) {
        return new User(signUp);
    }

    public User(SignUp signUp){
        this.userId = signUp.getUserId();
        this.password = signUp.getPassword();
        this.role = UserRole.ROLE_USER;
        this.isFriend = new IsFriend();
    }

    public User(String userId, String password){
        this.userId = userId;
        this.password = password;
    }



    public Integer findFriend(User friendUser) {
        return this.isFriend.getFriendList().get(friendUser.getId());
    }

    public void agreeFriend(User friendUser) {
        this.isFriend.getFriendList().put(friendUser.getId(), 3);
    }

    public void deleteFriend(User user) {
        this.isFriend.getFriendList().put(user.getId(), 0);
    }



    public boolean getIsEnable() {
        return true;
    }

    public void setFriend(User user) {
        this.isFriend.getFriendList().put(user.getId(), 2);
    }

    public void requestFriend(User friendUser) {
        this.isFriend.getFriendList().put(friendUser.getId(), 1);
    }

    public List<Long> findFriendRequestList() {
        HashMap<Long, Integer> friendList = this.isFriend.getFriendList();
        List<Long> friendRequestList = new ArrayList<>();

        ;
        for(Long userId : friendList.keySet()){
            if(friendList.get(userId)==1){
                friendRequestList.add(userId);
            }
        }
        return friendRequestList;

    }


}
