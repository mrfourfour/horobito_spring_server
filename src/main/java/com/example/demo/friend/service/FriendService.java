package com.example.demo.friend.service;

import com.example.demo.friend.domain.Friend;
import com.example.demo.friend.domain.FriendRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    private final FriendRepository friendRepository;

    public Page<Friend> findFriendListByPageAndPageSize(int page, int size) {
        Page<Friend> friends = friendRepository.findAll(PageRequest.of(page, size));
        return friends;
    }

    public void requestOrAcceptFriendByFriendId(Long friendId) {
        User user = null;
        User friendUser = userRepository.findByIdAndIsExist(friendId, false);

        if (user.findFriend(friendUser) == null) {
            friendUser.setFriend(user);
            user.setFriend(friendUser);
            Friend friend = new Friend(friendUser);
            friendRepository.save(friend);
        } else if (user.findFriend(friendUser) == false) {
            user.agreeFriend(friendUser);
            Friend friend = friendRepository.findByFriendId(friendId);
            friend.agreeFriend();
        }

    }

    public void deleteFriendByFriendId(Long friendId) {
        User user = null;
        User friendUser = userRepository.findByIdAndIsExist(friendId, false);
        if(friendUser.findFriend(friendUser)==false){
            friendUser.deleteFriend(user);
        }

    }

    public Page<Friend> findFriendRequest(int page, int size) {
        Page<Friend> requestedFriends = friendRepository.findAllFriend(false, PageRequest.of(page, size));
        return requestedFriends;
    }

}


