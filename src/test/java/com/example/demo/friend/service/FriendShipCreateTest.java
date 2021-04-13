package com.example.demo.friend.service;

import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.Password;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FriendShipCreateTest {


    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserSessionService userSessionService;

    @Mock
    UserService userService;



    @DisplayName("친구관계 맺기 테스트 1. 처음 신청")
    @Test
    void createFriendShip() throws AccessDeniedException {

        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
                userSessionService, userService);
        //given

        String[] friender = { "1", "jihwan"};
        String [] friendee = { "2", "friendee"};

        //when
        when(userService.findUserInfo())
                .thenReturn(friender);

        when(userService.findUserInfo(any()))
                .thenReturn(friendee);



        //then
        FriendShipResult result = friendShipService.create(1L);
        System.out.println(result);
        assertEquals(FriendShipResult.TRY_TO_MAKE_FRIENDSHIP, result);

    }

    @DisplayName("친구관계 맺기 테스트 2. 이미 신청 또는 수락된 경우 ")
    @Test
    void testForAlreadyAccept() throws AccessDeniedException {

        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
                userSessionService, userService);

        //given

        String[] friender = { "1", "jihwan"};
        String [] friendee = { "2", "friendee"};

        Friender friender1 = Friender.create(PersonId.create(1L), )

        Friendee friendee1;

        //when
        when(userService.findUserInfo())
                .thenReturn(friender);

        when(userService.findUserInfo(any()))
                .thenReturn(friendee);






        //then
        FriendShipResult result = friendShipService.create(1L);
        System.out.println(result);
        assertEquals(FriendShipResult.TRY_TO_MAKE_FRIENDSHIP, result);

    }


}