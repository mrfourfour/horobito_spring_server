package com.example.demo.friend.controller;


import com.example.demo.friend.service.FriendDto;
import com.example.demo.friend.service.FriendShipService;
import com.example.demo.friend.service.Result;
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
        Result result = friendShipService.dealWithFriendShip(friendId);

        if (result==Result.Try_to_make_FriendShip || result==Result.Accept){
            ResponseEntity.ok();
        }else if(result==Result.Already_Accept){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }else {
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriendShipRequest(@PathVariable Long friendId){
        friendShipService.deleteFriendShipRequest(friendId);
    }

    @GetMapping("/request")
    public void findRequestToMe(@RequestParam(value="page") int page,
                                @RequestParam(value = "size") int size){
        friendShipService.findRequestForMe();
    }
}
