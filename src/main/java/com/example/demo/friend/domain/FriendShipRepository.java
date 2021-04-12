package com.example.demo.friend.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByUserInfo(Friender user, Pageable pageable);

    List<Friendship> findAllByUserInfo(Friender userInfo);


    Friendship findFriendshipByUserInfoAndFriend_FriendId(Friender user, PersonId id);

    Friendship findFriendshipByFriend_FriendIdAndUserInfo_UserId(PersonId friendId, PersonId userId);

    Page<Friendship> findAllByFriend_FriendId(PersonId friendId, Pageable pageable);
}
