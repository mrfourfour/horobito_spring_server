package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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



}
