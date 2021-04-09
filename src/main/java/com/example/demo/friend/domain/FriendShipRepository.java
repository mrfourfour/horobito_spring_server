package com.example.demo.friend.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByUserInfo(UserInfo user, Pageable pageable);

    List<Friendship> findAllByUserInfo(UserInfo userInfo);


    Friendship findFriendshipByUserInfoAndFriend_FriendId(UserInfo user, Identfication id);

    Page<Friendship> findAllByFriend_FriendId(Identfication friendId, Pageable pageable);
}
