package com.example.demo.friend.domain;

import com.example.demo.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByUser(User user, Pageable pageable);


    Friendship findFriendshipByUserAndFriend_FriendId(User user, Long id);
}
