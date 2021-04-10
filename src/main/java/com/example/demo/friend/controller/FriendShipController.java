package com.example.demo.friend.controller;


import com.example.demo.friend.service.FriendDto;
import com.example.demo.friend.service.FriendShipService;
import com.example.demo.friend.service.FriendShipResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;

    @GetMapping
    // request query로 받는 값들
    public List<FriendDto> getMyFriends(@RequestParam(value="page") int page,
                                        @RequestParam(value = "size") int size) throws AccessDeniedException {
        return friendShipService.getMyFriends(page, size);

    }

    @PostMapping("/{friendId}")
    public void createFriendShip(@PathVariable Long friendId) throws AccessDeniedException {
        FriendShipResult friendShipResult = friendShipService.create(friendId);

        if (friendShipResult == FriendShipResult.TRY_TO_MAKE_FRIENDSHIP || friendShipResult == FriendShipResult.SUCCESS){
            ResponseEntity.ok();
        }else if(friendShipResult == FriendShipResult.ALREADY_ACCEPT){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }else {
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriendShip(@PathVariable Long friendId) throws AccessDeniedException {
        friendShipService.deleteFriendShipRequest(friendId);
    }

    @GetMapping("/request")
    public List<FriendDto> findRequestToMe(@RequestParam(value="page") int page,
                                @RequestParam(value = "size") int size) throws AccessDeniedException {
        return friendShipService.findRequestForMe(page, size);
    }
}
