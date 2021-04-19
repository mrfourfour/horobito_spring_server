package com.example.demo.friend.service;


import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendShipDeleteTest {

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserSessionService userSessionService;


    UserService userService = new UserService(userRepository, userSessionService);

    @DisplayName("친구관계 삭제 테스트 1. 친구 관계가 없는 경우  ")
    @Test
    void testForDeleteFail() throws AccessDeniedException {

        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
                 userService);

        //given

        String[] friender = { "1", "jihwan"};
        String [] friendee = { "2", "friendee"};


        //when
        when(userSessionService.getLoggeddUser()).thenReturn(userRepository.findUserById(2L));

        when(userService.findUserInfo())
                .thenReturn(friender);


        //then
        FriendShipResult result = friendShipService.deleteFriendShipRequest(1L);
        System.out.println(result);
        assertEquals(FriendShipResult.NEVER_REQUESTED, result);

    }



    @DisplayName("친구관계 삭제 테스트 2. 친구 관계 삭제  ")
    @Test
    void testForAccept() throws AccessDeniedException {

        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
                userService);

        //given

        String[] friender = {"1", "jihwan"};
        String[] friendee = {"2", "friendee"};

        PersonId myId = PersonId.create(Long.parseLong(friender[0]));
        PersonId friendId = PersonId.create(Long.parseLong(friendee[0]));

        PersonName myName = PersonName.create(friender[1]);
        PersonName friendName = PersonName.create(friendee[1]);

        Friender frienderMe = Friender.create(myId, myName);
        Friendee friendeeYou = Friendee.create(friendId, friendName);


        Friendship forwardFriendShip
                = Friendship.create(frienderMe, friendeeYou);
        forwardFriendShip.acceptFriendShip();

        //when
        when(userService.findUserInfo())
                .thenReturn(friender);


        when(friendShipRepository
                .findFriendshipByFrienderAndFriendee_FriendeeId(
                        any(), any()
                )).thenReturn(forwardFriendShip);


        //then
        FriendShipResult result = friendShipService.deleteFriendShipRequest(1L);
        System.out.println(forwardFriendShip.getFriendState());
        assertEquals(false, forwardFriendShip.getFriendState());

    }
}
