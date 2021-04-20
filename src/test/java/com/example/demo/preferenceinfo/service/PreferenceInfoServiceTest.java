package com.example.demo.preferenceinfo.service;

import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.preferenceinfo.domain.PreferenceInfoRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreferenceInfoServiceTest {


    @Mock
    FeedRepository feedRepository;
    @Mock
    UserService userService;
    @Mock
    PreferenceInfoRepository preferenceInfoRepository;
    @Mock
    FriendShipRepository friendShipRepository;
    @Mock
    CommentService commentService;


    @DisplayName("게시글 좋아요 테스트1, 해당 feed 없을 경우 ")
    @Test
    void firstTest() throws AccessDeniedException {
        //given
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        String[] userInfo = {"1", "hello"};


        //when
        when(userService.findUserInfo()).thenReturn(userInfo);

        assertThrows(NullPointerException.class, ()->sut.likeFeedByFeedId(1L));


    }

}