package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.FriendShipState;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.friend.domain.PersonId;
import com.example.demo.user.service.UserDto;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FriendShipRepository friendShipRepository;
    private final UserService userService;

    @Transactional
    public void deleteFeedByFeedId(Long id) throws AccessDeniedException, IllegalAccessException {
        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        PersonId myId = PersonId.create(userService.findUserInfo().getUserId());

        if (feed==null){
            throw new IllegalArgumentException();
        }

        if (myId.getId().equals(feed.getId())){
            feed.delete();
        }else {
            throw new IllegalAccessException();
        }
    }


    public FeedDto findFeedDetailByFeedId(Long id) throws AccessDeniedException, IllegalAccessException {
        UserDto userInfo = userService.findUserInfo();

        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        if (feed==null){
            throw new IllegalArgumentException();
        }

        PersonId myId = PersonId.create(userInfo.getUserId());
        PersonId friendId = PersonId.create(feed.getId());
        Friendship friendship = friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(myId, friendId);



        if (friendship==null || friendship.getFriendState()!=FriendShipState.ACCEPT){
            throw new IllegalAccessException();
        }


        FeedDto feedDto = toFeedDto(feed);
        return feedDto;
    }

    private FeedDto toFeedDto(Feed feed) {
        return new FeedDto(
                feed.getId(),
                feed.getWriter().getId(),
                feed.getWriter().getName(),
                feed.getContent().getContent(),
                feed.getComments().stream()
                        .map(this::toCommentDto).collect(Collectors.toList()),
                feed.getPreferenceCountInfo().getPreference(),
                feed.getWrtTime()
        );
    }

    private CommentDto toCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriter().getId(),
                comment.getWriter().getName(),
                comment.getContent(),
                comment.getPreferenceCountInfo().getPreference(),
                comment.getWrtTime()
        );

    }


    public FeedDto makeFeedByContents(String insertedTitle, String insertedContent) throws AccessDeniedException {

        if (insertedTitle.length()==0){
            throw new IllegalArgumentException();
        }

        UserDto userInfo = userService.findUserInfo();

        WriterId id = WriterId.create(userInfo.getUserId());
        WriterName wrtName = WriterName.create(userInfo.getUsername());
        Writer writer = Writer.create(id, wrtName);
        Content content = Content.create(insertedContent);
        Title title = Title.create(insertedTitle);
        Feed feed = Feed.create(writer, title, content);
        feedRepository.save(feed);

        FeedDto feedDto = toFeedDto(feed);
        return feedDto;

    }





}
