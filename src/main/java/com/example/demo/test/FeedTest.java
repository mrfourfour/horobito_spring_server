package com.example.demo.test;

import com.example.demo.friend.controller.FriendShipController;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.friend.service.FriendShipService;
import com.example.demo.user.domain.UserRepository;
import org.junit.jupiter.api.Test;

public class FeedTest {

    Friendship friendship = new Friendship();

    FriendShipService friendShipService = new FriendShipService(friendship);

    FriendShipController friendShipController = new FriendShipController(friendShipService);

    @Test

}
