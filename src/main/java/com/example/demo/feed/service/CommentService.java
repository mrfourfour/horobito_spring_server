package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.FriendShipState;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.friend.domain.PersonId;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import com.example.demo.user.service.UserDto;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final FeedRepository feedRepository;
    private final UserService userService;
    private final FriendShipRepository friendShipRepository;

    @Transactional
    public CommentDto makeCommentByFeedIdAndContents(Long feedId, String insertedContent) throws AccessDeniedException, IllegalAccessException {

        Feed feed = feedRepository.findFeedByIdAndDeleted(feedId, false);
        if (insertedContent.length()==0 || feed==null){
            throw new IllegalArgumentException();
        }
        UserDto userInfo = userService.findUserInfo();

        Friendship friendship = findFriendShip(feed, userInfo);


        if (friendship==null || friendship.getFriendState()!= FriendShipState.ACCEPT){
            throw new IllegalAccessException();
        }
        Comment comment = createComment(insertedContent, userInfo);
        feed.enrollComment(comment);


        CommentDto commentDto = toCommentDto(comment);
        return commentDto;
    }

    private Friendship findFriendShip(Feed feed, UserDto userInfo) throws AccessDeniedException {
        Long friendId = feed.getWriter().getId();
        return friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                PersonId.create(friendId),
                PersonId.create(userInfo.getUserId()));
    }


    private Comment createComment(String insertedContent, UserDto userInfo) {
        Content content = Content.create(insertedContent);
        Writer writer = createWriter(userInfo);
        return Comment.create(writer, content);
    }

    private Writer createWriter(UserDto userInfo) {
        WriterId id = WriterId.create(userInfo.getUserId());
        WriterName wrtName = WriterName.create(userInfo.getUsername());
        return Writer.create(id, wrtName);
    }

    private CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getWriter().getId(),
                comment.getWriter().getName(),
                comment.getContent(),
                comment.getPreferenceCountInfo().getPreference(),
                comment.getWrtTime()
        );
    }

    public Comment findCommentById(List<Comment> commentList, Long commentId){
        Comment result = null;
        for (Comment comment : commentList){
            if (comment.getId().equals(commentId)){
                return comment;

            }
        }
        return result;
    }


}
