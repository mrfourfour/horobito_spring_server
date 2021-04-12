package com.example.demo.preferenceinfo.service;


import com.example.demo.feed.domain.Comment;
import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Identfication;
import com.example.demo.preferenceinfo.domain.PreferenceStatus;
import com.example.demo.preferenceinfo.domain.PreferenceInfo;
import com.example.demo.preferenceinfo.domain.PreferenceLocation;
import com.example.demo.preferenceinfo.domain.PreferenceInfoRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class PreferenceInfoService {

    private final FeedRepository feedRepository;
    private final UserSessionService userSessionService;
    private final PreferenceInfoRepository preferenceInfoRepository;
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

        if (preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonId(feed.getId(), user.getId())==null){
            PreferenceInfo preferenceInfo = PreferenceInfo.create(user.getId(), feed.getId(), PreferenceLocation.FEED);
            preferenceInfo.locate(PreferenceLocation.FEED);
            preferenceInfo.like();
            preferenceInfoRepository.save(preferenceInfo);

            feed.like();

            return PreferenceResult.SUCCESS;

        }

        PreferenceInfo preferenceInfo
                    = preferenceInfoRepository
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
