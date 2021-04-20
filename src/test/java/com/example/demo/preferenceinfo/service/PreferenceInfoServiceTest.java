package com.example.demo.preferenceinfo.service;

import com.example.demo.feed.domain.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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

    @DisplayName("게시글 좋아요 테스트2, 게시글 작성자와 친구 관계가 아닐 경우")
    @Test
    void secondTest() throws AccessDeniedException {
        //given
        Long id = Long.parseLong("1");

        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        String[] userInfo = {"1", "hello"};

        WriterId writerId = WriterId.create(1L);
        WriterName writerName = WriterName.create("tempWriter");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("tmep");

        Feed feed = Feed.create(writer, content);


        //when
        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        assertThrows(IllegalStateException.class, ()->sut.likeFeedByFeedId(id));


    }

    @DisplayName("게시글 좋아요 테스트3, 내 게시글인 경우")
    @Test
    void thirdTest() throws AccessDeniedException {
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

        Long id = Long.parseLong("1");

        WriterId writerId = WriterId.create(id);
        WriterName writerName = WriterName.create("tempWriter");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("tmep");

        Feed feed = Feed.create(writer, content);


        //when
        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        assertThrows(IllegalAccessException.class, ()->sut.likeFeedByFeedId(id));


    }

}