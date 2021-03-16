package com.example.demo.friend.domain;

import com.example.demo.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    List<Friendship> findAllByUser(User user, Pageable pageable);

    List<Friendship> findAllByUser(User user);

    Friendship findFriendshipByUserAndFriend_FriendId(User user, Long id);
}
