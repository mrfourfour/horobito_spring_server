package com.example.demo.feed.service;

import com.example.demo.feed.domain.Content;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.domain.Title;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.user.service.UserDto;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreateFeedTest {

    @Mock
    FeedRepository feedRepository;

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserService userService;

    @DisplayName("게시글 만들기 1. 잘못된 글인 경우 ")
    @Test
    void test1() throws AccessDeniedException {
        FeedService feedService
                = new FeedService(
                        feedRepository,
                friendShipRepository,
                userService
        );


        String title = "";

        String content = "content 1";



        assertThrows(IllegalArgumentException.class,
                ()->feedService.makeFeedByContents(title, content));


    }

    @DisplayName("게시글 만들기 2. 올바른 요청인 경우 ")
    @Test
    void test2() throws AccessDeniedException {
        FeedService feedService
                = new FeedService(
                feedRepository,
                friendShipRepository,
                userService
        );

        Long id1 = Long.parseLong("1");
        UserDto person1Info = UserDto.create(id1,"hello");

        String title = "title1";

        String content = "content 1";


        when(userService.findUserInfo())
                .thenReturn(person1Info);



        System.out.println(feedService.makeFeedByContents(title, content));
        verify(feedRepository, times(1)).save(any());
    }



}