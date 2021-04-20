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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FriendShipCreateTest {


    @Mock
    FriendShipRepository friendShipRepository;


    @Mock
    UserService userService;



    @DisplayName("친구관계 맺기 테스트 1. 처음 신청")
    @Test
    void createFriendShip() throws AccessDeniedException {

        FriendShipService sut
                = new FriendShipService(friendShipRepository,
                userService);

        //given

        String[] friender = { "1", "jihwan"};
        String [] friendee = { "2", "friendee"};

        //when
        when(userService.findUserInfo())
                .thenReturn(friender);

        when(userService.findUserInfo(any()))
                .thenReturn(friendee);

        //then


    }

    @DisplayName("친구관계 맺기 테스트 2. 이미 신청 또는 수락된 경우 ")
    @Test
    void testForAlreadyAccept() throws AccessDeniedException {

        FriendShipService sut
                = new FriendShipService(friendShipRepository,
                 userService);

        //given

        String[] friender = { "1", "jihwan"};
        String [] friendee = { "2", "friendee"};

        PersonId myId = PersonId.create(Long.parseLong(friender[0]) );
        PersonId friendId = PersonId.create(Long.parseLong(friendee[0]));

        PersonName myName = PersonName.create(friender[1]);
        PersonName friendName = PersonName.create(friendee[1]);

        Friender frienderMe = Friender.create(myId, myName);
        Friendee friendeeYou = Friendee.create(friendId, friendName);

        Friender frienderYou = Friender.create(friendId, friendName);
        Friendee friendeeMe = Friendee.create(myId, myName);

        Friendship forwardFriendShip
                = Friendship.create(frienderMe, friendeeYou);
        forwardFriendShip.acceptFriendShip();

        Friendship backwardFriendShip
                = Friendship.create(frienderYou, friendeeMe);


        //when
        when(userService.findUserInfo())
                .thenReturn(friender);

        when(userService.findUserInfo(any()))
                .thenReturn(friendee);

        when(friendShipRepository
                .findFriendshipByFrienderAndFriendee_FriendeeId(
                        any(), any()
                )).thenReturn(forwardFriendShip)
                .thenReturn(forwardFriendShip)
                .thenReturn(backwardFriendShip);



        //then
        assertThrows(IllegalStateException.class, ()->sut.create(1L));
    }


    @DisplayName("친구관계 맺기 테스트 3. 요청 수락  ")
    @Test
    void testForAccept() throws AccessDeniedException {

        FriendShipService sut
                = new FriendShipService(friendShipRepository,
                 userService);

        //given

        String[] friender = { "1", "jihwan"};
        String [] friendee = { "2", "friendee"};

        PersonId myId = PersonId.create(Long.parseLong(friender[0]) );
        PersonId friendId = PersonId.create(Long.parseLong(friendee[0]));

        PersonName myName = PersonName.create((String) friender[1]);
        PersonName friendName = PersonName.create((String) friendee[1]);

        Friender frienderMe = Friender.create(myId, myName);
        Friendee friendeeYou = Friendee.create(friendId, friendName);

        Friender frienderYou = Friender.create(friendId, friendName);
        Friendee friendeeMe = Friendee.create(myId, myName);

        Friendship forwardFriendShip
                = Friendship.create(frienderMe, friendeeYou);


        Friendship backwardFriendShip
                = Friendship.create(frienderYou, friendeeMe);
        backwardFriendShip.requestFriendShip();

        System.out.println("forwardFriendShip의 상태 : " + forwardFriendShip.getFriendState());
        System.out.println("backwardFriendShip의 상태 : " + backwardFriendShip.getFriendState());



        //when
        when(userService.findUserInfo())
                .thenReturn(friender);

        when(userService.findUserInfo(any()))
                .thenReturn(friendee);

        when(friendShipRepository
                .findFriendshipByFrienderAndFriendee_FriendeeId(
                        any(), any()
                )).thenReturn(forwardFriendShip)
                .thenReturn(forwardFriendShip)
                .thenReturn(backwardFriendShip);

        //then

        sut.create(1L);
        System.out.println("forwardFriendShip의 상태 : " + forwardFriendShip.getFriendState());
        System.out.println("backwardFriendShip의 상태 : " + backwardFriendShip.getFriendState());


    }


}