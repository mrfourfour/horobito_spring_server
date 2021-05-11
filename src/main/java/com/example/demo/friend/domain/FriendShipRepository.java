package com.example.demo.friend.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendShipRepository  extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByFriender(Friender user, Pageable pageable);

    List<Friendship> findAllByFriender(Friender userInfo);


    Friendship findFriendshipByFrienderAndFriendee_FriendeeId(Friender user, PersonId id);

    Friendship findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(PersonId frienderId, PersonId friendeeId);

    Page<Friendship> findAllByFriendee_FriendeeId(PersonId friendId, Pageable pageable);
}
