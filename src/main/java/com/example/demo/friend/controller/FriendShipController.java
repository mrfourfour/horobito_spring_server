package com.example.demo.friend.controller;


import com.example.demo.friend.service.FriendDto;
import com.example.demo.friend.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;

    @GetMapping
    // request query로 받는 값들
    public List<FriendDto> getMyFriendList(@RequestParam(value="page") int page,
                                           @RequestParam(value = "size") int size){
        return friendShipService.getMyFriendList(page, size);

    }

    @PostMapping("/{friendId}")
    public String dealWithFriendShip(@PathVariable Long friendId){
        String result = friendShipService.dealWithFriendShip(friendId);

        if (result.equals("Accept") || result.equals("Already accept")){
            return "200 OK";
        }else if(result.equals("Already accept")){
            return "400 Bad Request";
        }else {
            return "홀ㄹ로홀로";
        }
    }
}
