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
    public ResponseEntity<List<FriendDto>> getMyFriends(@RequestParam(value="page") int page,
                                        @RequestParam(value = "size") int size) throws AccessDeniedException {

        try {
            List<FriendDto> result = friendShipService.getMyFriends(page, size);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }


    }

    @PostMapping("/{friendId}")
    public void createFriendShip(@PathVariable Long friendId) throws AccessDeniedException {
        try {
            friendShipService.create(friendId);
        }catch (Exception e){

        }


    }

    @DeleteMapping("/{friendId}")
    public void deleteFriendShip(@PathVariable Long friendId) throws AccessDeniedException {
        FriendShipResult result = friendShipService.deleteFriendShipRequest(friendId);

    }

    @GetMapping("/request")
    public Object findRequestToMe(@RequestParam(value="page") int page,
                                @RequestParam(value = "size") int size) throws AccessDeniedException {
        Object result = friendShipService.findRequestForMe(page, size);

        if (result instanceof FriendShipResult){
            switch ((FriendShipResult) result){
                case DENIED:
                    ResponseEntity.status(HttpStatus.BAD_REQUEST);
            }
        }else {
            ResponseEntity.ok();
        }
        return result;
    }
}
