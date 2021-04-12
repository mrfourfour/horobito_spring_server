package com.example.demo.preferredperson.service;


import com.example.demo.feed.domain.Comment;
import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Identfication;
import com.example.demo.preferredperson.domain.PreferenceStatus;
import com.example.demo.preferredperson.domain.PreferenceInfo;
import com.example.demo.preferredperson.domain.PreferredPersonInfoLocation;
import com.example.demo.preferredperson.domain.PreferredPersonRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class PreferredPersonService {

    private final FeedRepository feedRepository;
    private final UserSessionService userSessionService;
    private final PreferredPersonRepository preferredPersonRepository;
    private final FriendShipRepository friendShipRepository;
    private final CommentService commentService;


    @Transactional
    public PreferenceResult likeFeedByFeedId(Long id) throws AccessDeniedException {
        User user = getLoggedUser();
        Feed feed;

        if ((feed=feedRepository.findFeedByIdAndDeleted(id, false))==null){
            return PreferenceResult.FEED_NOT_FOUND;
        }

        if (friendShipRepository.findFriendshipByFriend_FriendIdAndUserInfo_UserId(
                Identfication.create(user.getId()), Identfication.create(feed.getWriter().getId()))==null){
            return PreferenceResult.NOT_MY_FRIEND;
        }

        if (feed.getWriter().getId().equals(user.getId())){
            return PreferenceResult.MY_FEED_ERROR;
        }

        if (preferredPersonRepository
                .findByDocumentIdAndPreferredPersonId(feed.getId(), user.getId())==null){
            PreferenceInfo preferenceInfo = PreferenceInfo.create(user.getId(), feed.getId(), PreferredPersonInfoLocation.FEED);
            preferenceInfo.locate(PreferredPersonInfoLocation.FEED);
            preferenceInfo.like();
            preferredPersonRepository.save(preferenceInfo);

            feed.like();

            return PreferenceResult.SUCCESS;

        }

        PreferenceInfo preferenceInfo
                    = preferredPersonRepository
                    .findByDocumentIdAndPreferredPersonId(feed.getId(), user.getId());

        if (preferenceInfo.findState()==PreferenceStatus.LIKE){
                preferenceInfo.disLike();
                feed.disLike();

                return PreferenceResult.SUCCESS;
        }

        if (preferenceInfo.findState()==PreferenceStatus.INDIFFERENCE){
                preferenceInfo.like();
                feed.like();
        }
        return PreferenceResult.SUCCESS;

    }

    @Transactional
    public PreferenceResult likeCommentByFeedIdAndCommentId(Long feedId, Long commentId) throws AccessDeniedException {
        User user = getLoggedUser();
        Feed feed;
        Comment comment;

        if ((feed=feedRepository.findFeedByIdAndDeleted(feedId, false))==null){
            return PreferenceResult.FEED_NOT_FOUND;
        }

        if (friendShipRepository.findFriendshipByFriend_FriendIdAndUserInfo_UserId(
                Identfication.create(user.getId()), Identfication.create(feed.getWriter().getId()))==null){
            return PreferenceResult.NOT_MY_FRIEND;
        }

        if((comment = commentService.findCommentById(feed.getComments(), commentId))==null){
            return PreferenceResult.COMMENT_NOT_FOUND;
        }

    }

    private User getLoggedUser() throws AccessDeniedException {
        return userSessionService.getLoggeddUser();
    }




}
