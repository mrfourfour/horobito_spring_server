package com.example.demo.friend.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByUserInfo(UserInfo user, Pageable pageable);


    Friendship findFriendshipByUserInfoAndFriendAndFriend_FriendId(UserInfo user, FriendId friendId);
}
