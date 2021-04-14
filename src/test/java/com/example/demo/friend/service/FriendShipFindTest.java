package com.example.demo.friend.service;


import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FriendShipFindTest {

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserSessionService userSessionService;

    @Mock
    UserService userService;
}
