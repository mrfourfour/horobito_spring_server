package com.example.demo.preferenceinfo.service;


import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.preferenceinfo.domain.PreferenceInfoRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentLikeTest {

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

    @Mock
    Friendship friendship;



    @DisplayName("댓글 좋아요 테스트 1 : Feed 없을 때")
    @Test
    void test1() throws AccessDeniedException {
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        String[] userInfo = {"1", "hello"};

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");




        when(userService.findUserInfo()).thenReturn(userInfo);

        assertThrows(IllegalArgumentException.class, ()->sut
                .likeCommentByFeedIdAndCommentId(feedId,commendId));


    }



}
