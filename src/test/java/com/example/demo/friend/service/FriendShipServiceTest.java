package com.example.demo.friend.service;

import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.Password;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
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
class FriendShipServiceTest {

    @Mock
    FeedRepository feedRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserSessionService userSessionService;

    User user;
    PersonId personId;
    Friendee friendee;
    Friender friender;


    @DisplayName("친구관계 맺기 테스트 1. 처음 신청")
    @Test
    void createFriendShip() throws AccessDeniedException {
        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
                userRepository,
                userSessionService);
        //given
        PersonName myName = PersonName.create("jihwan");
        Password myPassword = Password.create("1234");

        Friender me = Friender.create(PersonId.create(1L), myName);


        PersonName friendName = PersonName.create("jihwan");
        Password friendPassword = Password.create("1234");

        when(userSessionService.getLoggeddUser())
                .thenReturn(null);

        when(userRepository.findUserById(anyLong()))
                .thenReturn(null);

        when(PersonId.create(anyLong()))
                .thenReturn(null);

        when(friendShipService.creat)


        //when
        FriendShipResult result = friendShipService.create(any());

        //then
        assertEquals(FriendShipResult.SUCCESS, result);

    }


}