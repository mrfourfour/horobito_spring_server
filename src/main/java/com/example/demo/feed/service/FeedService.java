package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.FriendShipState;
import com.example.demo.friend.domain.Friendship;
import com.example.demo.friend.domain.PersonId;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpResponse;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserSessionService userSessionService;
    private final FriendShipRepository friendShipRepository;
    private final UserService userService;

    @Transactional
    public RequestResult deleteFeedByFeedId(Long id) throws AccessDeniedException {
        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        PersonId myId = PersonId.create(Long.parseLong(userService.findUserInfo()[0]));
        PersonId friendId = PersonId.create(feed.getId());
        Friendship friendship;

        if ((friendship=friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(myId, friendId))==null){
            return RequestResult.BAD_REQUEST;
        }

        if (myId.getId().equals(friendId.getId())){

        }else if (friendship.getFriendState()!= FriendShipState.ACCEPT){
            return RequestResult.UNAUTHORIZED;
        }

        feed.delete();
        return RequestResult.OK;
    }


    public Feed findFeedDetailByFeedId(Long id) {
        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        return feed;
    }



    public void makeFeedByContents(String InsertedContent) throws AccessDeniedException {

        User user = userSessionService.getLoggeddUser();

        WriterId id = WriterId.create(user.getId());
        WriterName wrtName = WriterName.create(user.getUserBasicInfo().getUsername());
        Writer writer = Writer.create(id, wrtName);
        Content content = Content.create(InsertedContent);
        Feed feed = Feed.create(writer, content);


        feedRepository.save(feed);
        System.out.println("새로운 feed 생성 완료");
    }





}
