package com.example.demo.preferenceinfo.service;

import com.example.demo.feed.domain.*;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Friender;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.preferenceinfo.domain.PreferenceInfo;
import com.example.demo.preferenceinfo.domain.PreferenceInfoRepository;
import com.example.demo.preferenceinfo.domain.PreferenceLocation;
import com.example.demo.preferenceinfo.domain.PreferenceStatus;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedLikeTest {


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

        Title title = Title.create("This is title");

        Feed feed = Feed.create(writer, title, content);


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

        Title title = Title.create("This is title");

        Feed feed = Feed.create(writer, title, content);


        //when
        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        assertThrows(IllegalAccessException.class, ()->sut.likeFeedByFeedId(id));

    }

    @DisplayName("게시글 좋아요 테스트4, 처음 [좋아요] 하는 경우 ")
    @Test
    void test4() throws AccessDeniedException, IllegalAccessException {
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

        Long userId = Long.parseLong("1");

        Long friendId = Long.parseLong("3");

        Long feedId = Long.parseLong("2");

        Long commentId = Long.parseLong("4");




        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Title title = Title.create("This is title");

        Feed feed = FeedHelper.create(feedId, writer, title,  content);





        System.out.println("테스트 전 feed : " );
        System.out.println(feed.getPreferenceCountInfo().getPreference());


        //when
        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);



        when(friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                any(), any()
        )).thenReturn(friendship);



        //then
        sut.likeFeedByFeedId(1L);

        // mock 객체밖에 못 담는다.
        verify(preferenceInfoRepository, times(1))
                .save(any());

        // 검증 같은 경우  verify() : stub 검사  or assert(): 내 반환값이 뭐와 일치하는지
        //

        System.out.println("테스트 후 feed : " );
        System.out.println(feed.getPreferenceCountInfo().getPreference());


    }

    @DisplayName("게시글 좋아요 테스트5,[좋아요 취소하는 경우] ")
    @Test
    void test5() throws AccessDeniedException, IllegalAccessException {
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

        Long userId = Long.parseLong("1");

        Long friendId = Long.parseLong("3");

        Long feedId = Long.parseLong("2");




        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");

        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");

        Title title = Title.create("This is title");

        Feed feed = FeedHelper.create(feedId, writer, title, content);

        PreferenceInfo preferenceInfo
                = PreferenceInfo.create(userId, feedId);

        preferenceInfo.like();



        System.out.println("테스트 전 feed : " );
        System.out.println(feed.getPreferenceCountInfo().getPreference());
        System.out.println("테스트 전 PreferenceInfo :" );
        System.out.println(preferenceInfo.getPreferenceStatus());


        //when
        when(userService.findUserInfo()).thenReturn(userInfo);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);



        when(friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                any(), any()
        )).thenReturn(friendship);


        when(preferenceInfoRepository.findByDocumentIdAndPreferredPersonIdAndLocation(
                any(), any(), any()
        )).thenReturn(preferenceInfo);


        //then
        sut.likeFeedByFeedId(1L);

        assertEquals(PreferenceStatus.INDIFFERENCE, preferenceInfo.getPreferenceStatus());
        assertEquals(-1, feed.getPreferenceCountInfo().getPreference());

        // 검증 같은 경우  verify() : stub 검사  or assert(): 내 반환값이 뭐와 일치하는지
        //

        System.out.println("테스트 후 feed : " );
        System.out.println(feed.getPreferenceCountInfo().getPreference());
        System.out.println("테스트 후 PreferenceInfo :" );
        System.out.println(preferenceInfo.getPreferenceStatus());


    }

}