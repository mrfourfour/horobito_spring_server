package com.example.demo.friend.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByUserInfo(UserInfo user, Pageable pageable);


    Friendship findFriendshipByUserInfoAndFriend_FriendId(UserInfo user, Identfication id);

    Page<Friendship> findAllByFriend_FriendId(Identfication friendId, Pageable pageable);
}
