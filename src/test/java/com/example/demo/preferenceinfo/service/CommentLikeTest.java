package com.example.demo.preferenceinfo.service;


import com.example.demo.feed.domain.*;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.friend.domain.PersonId;
import com.example.demo.preferenceinfo.domain.PreferenceInfo;
import com.example.demo.preferenceinfo.domain.PreferenceInfoRepository;
import com.example.demo.user.service.UserDto;
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
import static org.mockito.Mockito.*;

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

        Long id1 = Long.parseLong("1");
        UserDto userInfo = UserDto.create(id1,"hello");

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");




        when(userService.findUserInfo()).thenReturn(userInfo);

        assertThrows(IllegalArgumentException.class, ()->sut
                .likeCommentByFeedIdAndCommentId(feedId,commendId));


    }

    @DisplayName("댓글 좋아요 테스트 2 : Feed 있지만, Comment 없을 경우")
    @Test
    void test2() throws AccessDeniedException {
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        Long id1 = Long.parseLong("1");
        UserDto userInfo = UserDto.create(id1,"hello");

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");

        Title title = Title.create("This is title");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Feed feed = FeedHelper.create(feedId, writer, title, content);




        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        // stubbing 을 하더라도, 일단 그에 맞는 타입이 들어오는게 보장되어야 동작을 한다는 것에 주의해라
        when(commentService.findCommentById(anyList(), anyLong()))
                .thenReturn(null);


        assertThrows(IllegalArgumentException.class, ()->sut
                .likeCommentByFeedIdAndCommentId(feedId,commendId));

        verify(commentService, times(1))
                .findCommentById(anyList(), anyLong());

    }

    @DisplayName("댓글 좋아요 테스트 3 : 친구관계가 아닌 경우")
    @Test
    void test3() throws AccessDeniedException {
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        Long id1 = Long.parseLong("1");
        UserDto userInfo = UserDto.create(id1,"hello");

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Title title = Title.create("This is title");

        Content content = Content.create("content");

        Feed feed = FeedHelper.create(feedId, writer, title, content);

        Comment comment = Comment.create(writer, content);


        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        // stubbing 을 하더라도, 일단 그에 맞는 타입이 들어오는게 보장되어야 동작을 한다는 것에 주의해라
        when(commentService.findCommentById(anyList(), anyLong()))
                .thenReturn(comment);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                       any()
                        , any()))
                .thenReturn(null);


        assertThrows(IllegalStateException.class, ()->sut
                .likeCommentByFeedIdAndCommentId(feedId,commendId));

        verify(commentService, times(1))
                .findCommentById(anyList(), anyLong());

    }

    @DisplayName("댓글 좋아요 테스트 4 : 좋아요 첫 경험 ")
    @Test
    void test4() throws AccessDeniedException {
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        Long id1 = Long.parseLong("1");
        UserDto userInfo = UserDto.create(id1,"hello");

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Title title = Title.create("This is title");

        Feed feed = FeedHelper.create(feedId, writer,title, content);

        Comment comment = CommentHelper.create(commendId, writer, content);

        Friendship friendship = mock(Friendship.class);


        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        // stubbing 을 하더라도, 일단 그에 맞는 타입이 들어오는게 보장되어야 동작을 한다는 것에 주의해라
        when(commentService.findCommentById(anyList(), anyLong()))
                .thenReturn(comment);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any()
                        , any()))
                .thenReturn(friendship);

        System.out.println(comment.getPreferenceCountInfo().getPreference());

        sut.likeCommentByFeedIdAndCommentId(feedId, commendId);

        verify(preferenceInfoRepository, times(1))
                .save(any());

        System.out.println(comment.getPreferenceCountInfo().getPreference());

    }

    @DisplayName("댓글 좋아요 테스트 5 : 좋아요 철회 ")
    @Test
    void test5() throws AccessDeniedException {
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        Long id1 = Long.parseLong("1");
        UserDto userInfo = UserDto.create(id1,"hello");

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Title title = Title.create("This is title");

        Feed feed = FeedHelper.create(feedId, writer, title, content);

        Comment comment = CommentHelper.create(commendId, writer, content);

        Friendship friendship = mock(Friendship.class);

        PreferenceInfo preferenceInfo
                = PreferenceInfo.create(userId , commendId);
        preferenceInfo.like();



        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        // stubbing 을 하더라도, 일단 그에 맞는 타입이 들어오는게 보장되어야 동작을 한다는 것에 주의해라
        when(commentService.findCommentById(anyList(), anyLong()))
                .thenReturn(comment);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any()
                        , any()))
                .thenReturn(friendship);

        when(preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonIdAndLocation(
                        any()
                        , any(), any()))
                .thenReturn(preferenceInfo);

//        when(preferenceInfoRepository
//                .findByDocumentIdAndPreferredPersonIdAndLocation());

        System.out.println(comment.getPreferenceCountInfo().getPreference());

        sut.likeCommentByFeedIdAndCommentId(feedId, commendId);

        verify(preferenceInfoRepository, times(0))
                .save(any());



        System.out.println(comment.getPreferenceCountInfo().getPreference());

    }

    @DisplayName("댓글 좋아요 테스트 6 : 다시 좋아요 ")
    @Test
    void test6() throws AccessDeniedException {
        PreferenceInfoService sut =
                new PreferenceInfoService(
                        feedRepository,
                        preferenceInfoRepository,
                        friendShipRepository,
                        userService,
                        commentService
                );

        Long id1 = Long.parseLong("1");
        UserDto userInfo = UserDto.create(id1,"hello");

        Long userId = Long.parseLong("1");

        Long feedId = Long.parseLong("2");

        Long friendId = Long.parseLong("3");

        Long commendId = Long.parseLong("4");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Title title = Title.create("This is title");

        Feed feed = FeedHelper.create(feedId, writer, title, content);

        Comment comment = CommentHelper.create(commendId, writer, content);

        Friendship friendship = mock(Friendship.class);

        PreferenceInfo preferenceInfo
                = PreferenceInfo.create(userId , commendId);
        preferenceInfo.disLike();



        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        // stubbing 을 하더라도, 일단 그에 맞는 타입이 들어오는게 보장되어야 동작을 한다는 것에 주의해라
        when(commentService.findCommentById(anyList(), anyLong()))
                .thenReturn(comment);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any()
                        , any()))
                .thenReturn(friendship);

        when(preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonIdAndLocation(
                        any()
                        , any(), any()))
                .thenReturn(preferenceInfo);

//        when(preferenceInfoRepository
//                .findByDocumentIdAndPreferredPersonIdAndLocation());

        System.out.println(comment.getPreferenceCountInfo().getPreference());

        sut.likeCommentByFeedIdAndCommentId(feedId, commendId);

        verify(preferenceInfoRepository, times(0))
                .save(any());



        System.out.println(comment.getPreferenceCountInfo().getPreference());

    }



}
