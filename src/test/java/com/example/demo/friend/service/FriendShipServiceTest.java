package com.example.demo.friend.service;

import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Identfication;
import com.example.demo.friend.domain.UserInfo;
import com.example.demo.user.domain.Password;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FriendShipServiceTest {

    @Mock
    FeedRepository feedRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    FriendShipRepository friendShipRepository;



    @Test
    void createFriendShip(){

        FriendShipService friendShipService = new FriendShipService(friendShipRepository, userRepository);

        Username myName = Username.create("MyName");
        Password myPassword = Password.create("MyPassword");

        User me = User.create(myName, myPassword);

        Identfication myId = Identfication.create(1L);

        Username friendName = Username.create("FriendName");
        Password friendPassword = Password.create("FriendPassword");

        User friend = User.create(friendName, friendPassword);

        Identfication friendId = Identfication.create(2L);

        when(friendShipService.findUser()).thenReturn(me).thenReturn(friend);



        when(Identfication.create(any()))
                .thenReturn(myId)
                .thenReturn(friendId);

        when(UserInfo.create(any(), any()))
                .thenReturn(UserInfo.create(myId, myName))



    }

}