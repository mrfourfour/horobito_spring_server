package com.example.demo.friend.service;

import com.example.demo.friend.domain.Friend;
import com.example.demo.friend.domain.FriendRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    private final FriendRepository friendRepository;



    @PreAuthorize("hasRole('ROLE_USER')")
    public Page<Friend> findFriendListByPageAndPageSize(int page, int size) {
        Page<Friend> friends = friendRepository.findAll(PageRequest.of(page, size));
        return friends;
    }


    public String requestOrAcceptFriendByFriendId(Long friendId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserId(authentication.getName());
        User friendUser = userRepository.findUserById(friendId);

        if (user.findFriend(friendUser) == 0) {
            friendUser.setFriend(user);
            user.requestFriend(friendUser);
            return "200 OK";
        } else if (user.findFriend(friendUser) == 2) {
            user.agreeFriend(friendUser);
            friendUser.agreeFriend(user);
            Friend friend = new Friend(friendUser);
            friendRepository.save(friend);
            return "200 OK";
        } else if (friendUser.findFriend(user) != 0){
            return "400 Bad Request";
        } else{
            return null;
        }

    }

    public String deleteFriendByFriendId(Long friendId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserId(authentication.getName());
        User friendUser = userRepository.findUserById(friendId);
        if(friendUser.findFriend(user)!=0){
            friendUser.deleteFriend(user);
            user.deleteFriend(friendUser);
            return "200 OK";
        } else if(friendUser.findFriend(user)==0){
            return "400 Bad Request";
        } else {
            return "403 Forbidden";
        }

    }

    public Page<String> findFriendRequest(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserId(authentication.getName());
        getFriendList(user.findFriendRequestList());
        Page<String> friendList
                = new PageImpl<>(getFriendList(user.findFriendRequestList()), PageRequest.of(page, size), 10000);
        return friendList;
    }

    private List<String> getFriendList(List<Long> friendRequestList) {
        List<String> friendsInfos = new ArrayList<>();
        for (Long id : friendRequestList){
            User user = userRepository.findUserById(id);
            friendsInfos.add("userId : " + user.getId() + "& userName : " + user.getUserId() );
        }
        return friendsInfos;
    }

}


