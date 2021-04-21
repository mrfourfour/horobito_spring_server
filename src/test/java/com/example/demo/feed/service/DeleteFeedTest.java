package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteFeedTest {

    @Mock
    FeedRepository feedRepository;

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserService userService;

    @DisplayName("게시글 삭제 1. 잘못된 글인 경우 ")
    @Test
    void test1() throws AccessDeniedException {
        FeedService sut
                = new FeedService(
                feedRepository,
                friendShipRepository,
                userService
        );

        String[] person1 = {"1" , "jihwan"};

        String title = "";

        String content = "content 1";

        when(userService.findUserInfo())
                .thenReturn(person1);

        assertThrows(IllegalArgumentException.class,
                ()->sut.deleteFeedByFeedId(1L));


    }

    @DisplayName("게시글 삭제 2.타인의 글에 삭제를 시도하였을 경우 ")
    @Test
    void test2() throws AccessDeniedException {
        FeedService sut
                = new FeedService(
                feedRepository,
                friendShipRepository,
                userService
        );

        String[] userInfo = {"1", "hello"};

        Long friendId = Long.parseLong("3");

        Long feedId = Long.parseLong("2");



        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Title title = Title.create("This is title");

        Feed feed = FeedHelper.create(feedId, writer, title,  content);

        when(userService.findUserInfo())
                .thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        assertThrows(IllegalAccessException.class,
                ()->sut.deleteFeedByFeedId(1L));


    }
}
