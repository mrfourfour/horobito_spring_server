package com.example.demo.user.service;

import com.example.demo.user.domain.Friend;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;



    public List<Friend> findFriendListByPageAndPageSize(int page, int size) {
        User user = null;
        List<Friend> friends = user.getFriends();
        return friends;
    }


    public void deleteFriendByFriendId(Long friendId) {
        User user = null;
        List<Friend> friends = user.getFriends();
        friends.remove(friendId);


    }

    public void requestOrAcceptFriendByFriendId(Long friendId) {
        User user = null;
        Friend friend = Friend.makeFriend(friendId);


    }


}
