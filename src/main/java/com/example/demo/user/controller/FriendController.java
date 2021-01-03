package com.example.demo.user.controller;


import com.example.demo.user.domain.Friend;
import com.example.demo.user.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public Friend[] findFriendListByPageAndSize(@RequestParam(value = "page")Long page,
                                                @RequestParam(value = "size")Long size){

    }

    @PostMapping("/friends/{friendId}")
    public String requestFriendShipByFriendId(@PathVariable Long id){

    }

    @PostMapping("/friends/{friendId}")
    public void deleteFriendByFriendId(@PathVariable Long id){

    }

    @GetMapping("/friends/request")
    public Friend[] findRequestByPageAndSize(@RequestParam(value = "page")Long page,
                                         @RequestParam(value = "size")Long size){

    }
}
