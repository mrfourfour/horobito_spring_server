package com.example.demo.friend.service;

import lombok.Getter;
import lombok.Value;
@Getter
@Value
public class FriendDto {

    Long friendId;
    String friendName;

    public FriendDto(Long friendId, String friendName) {
        this.friendId = friendId;
        this.friendName = friendName;
    }
}
