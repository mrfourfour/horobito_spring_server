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
public class CreateCommentTest {

    @Mock
    FeedRepository feedRepository;

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock

    UserService userService;



    @DisplayName("댓글 생성 1. 댓글을 달  feed 없는 경우  " )
    @Test
    void test1(){
        CommentService sut
                = new CommentService(feedRepository, userService, friendShipRepository);

        Long commentId = Long.parseLong("3");
        String commentContent = "conecontecno";


        assertThrows(IllegalArgumentException.class, ()->
                sut.makeCommentByFeedIdAndContents(commentId, commentContent));
    }

    @DisplayName("게시글 생성 2. feed는 있지만, 내용이 없을 경우  " )
    @Test
    void test2(){
        CommentService sut
                = new CommentService(feedRepository, userService, friendShipRepository);

        Long commentId = Long.parseLong("3");
        String commentContent = "";

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

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);



        assertThrows(IllegalArgumentException.class, ()->
                sut.makeCommentByFeedIdAndContents(commentId, commentContent));
    }

    @DisplayName("댓글 생성 3. feed 작성자와 친구가 아닐 경우 1) 아예 없는 경우 " )
    @Test
    void test3() throws AccessDeniedException {
        CommentService sut
                = new CommentService(feedRepository, userService, friendShipRepository);

        Long commentId = Long.parseLong("3");
        String commentContent = "asdf";

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

        Friender friender = Friender.create(person1Id, person1Name);
        Friendee friendee = Friendee.create(person2Id, person2Name);

        Friendship friendship = Friendship.create(friender, friendee);
        friendship.acceptFriendShip();

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        when(userService.findUserInfo())
                .thenReturn(person1Info);

        assertThrows(IllegalAccessException.class, ()->
                sut.makeCommentByFeedIdAndContents(commentId, commentContent));
    }

    @DisplayName("댓글 생성 4. feed 작성자와 친구가 아닐 경우 2) 친구관계가 아닌 경우 " )
    @Test
    void test4() throws AccessDeniedException {
        CommentService sut
                = new CommentService(feedRepository, userService, friendShipRepository);

        Long commentId = Long.parseLong("3");
        String commentContent = "asdf";

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

        Friender friender = Friender.create(person1Id, person1Name);
        Friendee friendee = Friendee.create(person2Id, person2Name);

        Friendship friendship = Friendship.create(friender, friendee);
        friendship.requestFriendShip();

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        when(userService.findUserInfo())
                .thenReturn(person1Info);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any(),
                        any())).thenReturn(friendship);

        assertThrows(IllegalAccessException.class, ()->
                sut.makeCommentByFeedIdAndContents(commentId, commentContent));
    }

    @DisplayName("댓글 생성 5. 댓글 생성 테스트" )
    @Test
    void test5() throws AccessDeniedException, IllegalAccessException {
        CommentService sut
                = new CommentService(feedRepository, userService, friendShipRepository);

        Long commentId = Long.parseLong("3");
        String commentContent = "나오면 성공";

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

        Friender friender = Friender.create(person1Id, person1Name);
        Friendee friendee = Friendee.create(person2Id, person2Name);

        Friendship friendship = Friendship.create(friender, friendee);
        friendship.acceptFriendShip();

        when(feedRepository.findFeedByIdAndDeleted(any(), any()))
                .thenReturn(feed);

        when(userService.findUserInfo())
                .thenReturn(person1Info);

        when(friendShipRepository
                .findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                        any(),
                        any())).thenReturn(friendship);

        System.out.println( sut.makeCommentByFeedIdAndContents(commentId, commentContent));
    }

}
