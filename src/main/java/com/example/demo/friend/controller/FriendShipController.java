package com.example.demo.friend.controller;


import com.example.demo.friend.service.FriendDto;
import com.example.demo.friend.service.FriendShipService;
import com.example.demo.friend.service.FriendShipResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;

    @GetMapping
    // request query로 받는 값들
    public List<FriendDto> getMyFriends(@RequestParam(value="page") int page,
                                        @RequestParam(value = "size") int size){
        return friendShipService.getMyFriends(page, size);

    }

    @PostMapping("/{friendId}")
    public void createFriendShip(@PathVariable Long friendId){
        FriendShipResult friendShipResult = friendShipService.create(friendId);

        if (friendShipResult == FriendShipResult.Try_to_make_FriendShip || friendShipResult == FriendShipResult.Accept){
            ResponseEntity.ok();
        }else if(friendShipResult == FriendShipResult.Already_Accept){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }else {
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriendShip(@PathVariable Long friendId){
        friendShipService.deleteFriendShipRequest(friendId);
    }

    @GetMapping("/request")
    public List<FriendDto> findRequestToMe(@RequestParam(value="page") int page,
                                @RequestParam(value = "size") int size){
        return friendShipService.findRequestForMe(page, size);
    }
}
