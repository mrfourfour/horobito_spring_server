package com.example.demo.friend.controller;


import com.example.demo.friend.service.FriendDto;
import com.example.demo.friend.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void dealWithFriendShip(@PathVariable Long friendId){
        String result = friendShipService.dealWithFriendShip(friendId);

        if (result.equals("Accept") || result.equals("Already accept")){
            ResponseEntity.ok();
        }else if(result.equals("Already accept")){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }else {
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }
}
