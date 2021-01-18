package com.example.demo.user.controller;


import com.example.demo.user.domain.Friend;
import com.example.demo.user.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public List<Friend> findFriendList(@RequestParam(value = "page")int page,
                                                    @RequestParam(value = "size")int size){
        return friendService.findFriendListByPageAndPageSize(page, size);

    }

    @PostMapping("/friends/{friendId}")
    public String requestFriend(@PathVariable Long FriendId){
        friendService.requestOrAcceptFriendByFriendId(FriendId);
    }

    @PostMapping("/friends/{friendId}")
    public void deleteFriend(@PathVariable Long friendId){
        friendService.deleteFriendByFriendId(friendId);

    }

    @GetMapping("/friends/request")
    public List<Friend> findRequest(@RequestParam(value = "page")Long page,
                                         @RequestParam(value = "size")Long size){

    }
}
