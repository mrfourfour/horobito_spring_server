package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.friend.domain.*;
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
public class FindFeedDetailTest {

    @Mock
    FeedRepository feedRepository;

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock

    UserService userService;
    @DisplayName("게시글 삭제 1. 해당 feed가 없을 경우 ")
    @Test
    void test1() throws AccessDeniedException {
        FeedService sut
                = new FeedService(
                feedRepository,
                friendShipRepository,
                userService
        );



        String[] person1 = {"1" , "jihwan"};

        Long id = Long.parseLong("2");

        when(userService.findUserInfo())
                .thenReturn(person1);


        assertThrows(IllegalArgumentException.class,
                ()->sut.findFeedDetailByFeedId(id));

    }

    @DisplayName("게시글 상세 조회 2. 해당 피드 작성자와 친구 관계가 아닐 경우 ")
    @Test
    void test2() throws AccessDeniedException {
        FeedService sut
                = new FeedService(
                feedRepository,
                friendShipRepository,
                userService
        );

        String[] person1Info = {"1", "hello"};

        Long friendId = Long.parseLong("3");
        String friendName = "hhhh";
        Long feedId = Long.parseLong("2");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");
        Writer writer = Writer.create(writerId, writerName);

        PersonId person1Id = PersonId.create(Long.parseLong(person1Info[0]));
        PersonId person2Id = PersonId.create(friendId);

        PersonName person1Name = PersonName.create( person1Info[1]);
        PersonName person2Name = PersonName.create( friendName);




        Content content = Content.create("content");
        Title title = Title.create("This is title");
        Feed feed = FeedHelper.create(feedId, writer, title,  content);

        Friender friender = Friender.create(person1Id, person1Name);
        Friendee friendee = Friendee.create(person2Id, person2Name);

        Friendship friendship = Friendship.create(friender, friendee);
        friendship.deleteFriendShip();

        when(userService.findUserInfo())
                .thenReturn(person1Info);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any(),
                        any())).thenReturn(friendship);

        assertThrows(IllegalAccessException.class,
                ()->sut.findFeedDetailByFeedId(feedId));

    }


    @DisplayName("게시글 상세 조회 3. FeedDto 반환 확인 코드 ")
    @Test
    void test3() throws AccessDeniedException, IllegalAccessException {
        FeedService sut
                = new FeedService(
                feedRepository,
                friendShipRepository,
                userService
        );

        String[] person1Info = {"1", "hello"};

        Long friendId = Long.parseLong("3");
        String friendName = "hhhh";
        Long feedId = Long.parseLong("2");

        WriterId writerId = WriterId.create(friendId);
        WriterName writerName = WriterName.create("writerName");
        Writer writer = Writer.create(writerId, writerName);

        Content content = Content.create("content");
        Title title = Title.create("This is title");
        Feed feed = FeedHelper.create(feedId, writer, title,  content);

        PersonId person1Id = PersonId.create(Long.parseLong(person1Info[0]));
        PersonId person2Id = PersonId.create(friendId);

        PersonName person1Name = PersonName.create( person1Info[1]);
        PersonName person2Name = PersonName.create( friendName);

        Comment comment1 = Comment.create(writer, content);
        Comment comment2 = Comment.create(writer, content);
        feed.enrollComment(comment1);
        feed.enrollComment(comment2);
        Friender friender = Friender.create(person1Id, person1Name);
        Friendee friendee = Friendee.create(person2Id, person2Name);

        Friendship friendship = Friendship.create(friender, friendee);
        friendship.acceptFriendShip();

        when(userService.findUserInfo())
                .thenReturn(person1Info);

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any(),
                        any())).thenReturn(friendship);

        System.out.println(sut.findFeedDetailByFeedId(feedId));

    }
}
