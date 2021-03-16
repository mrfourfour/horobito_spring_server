package com.example.demo.friend.controller;


import com.example.demo.friend.domain.Friend;
import com.example.demo.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public Page<Friend> findFriendList(@RequestParam(value = "page")int page,
                                       @RequestParam(value = "size")int size){
        return friendService.findFriendListByPageAndPageSize(page, size);

    }

    @PostMapping("/friends/{friendId}")
    public void requestFriend(@PathVariable Long FriendId){
        friendService.requestOrAcceptFriendByFriendId(FriendId);
    }

    @DeleteMapping("/friends/{friendId}")
    public void deleteFriend(@PathVariable Long friendId){
        friendService.deleteFriendByFriendId(friendId);


    }

    @GetMapping("/friends/request")
    public Page<String> findRequest(@RequestParam(value = "page")int page,
                                         @RequestParam(value = "size")int size){
        return friendService.findFriendRequest(page, size);

    }
}
