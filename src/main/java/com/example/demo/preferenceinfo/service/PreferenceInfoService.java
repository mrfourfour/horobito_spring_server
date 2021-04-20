package com.example.demo.preferenceinfo.service;


import com.example.demo.feed.domain.Comment;
import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.service.CommentService;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.PersonId;
import com.example.demo.preferenceinfo.domain.PreferenceStatus;
import com.example.demo.preferenceinfo.domain.PreferenceInfo;
import com.example.demo.preferenceinfo.domain.PreferenceLocation;
import com.example.demo.preferenceinfo.domain.PreferenceInfoRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class PreferenceInfoService {

    private final FeedRepository feedRepository;
    private final PreferenceInfoRepository preferenceInfoRepository;
    private final FriendShipRepository friendShipRepository;
    private final UserService userService;
    private final CommentService commentService;


    @Transactional
    public void likeFeedByFeedId(Long id) throws AccessDeniedException, IllegalAccessException {
        String[] userInfo = userService.findUserInfo();
        Feed feed;

        if ((feed=feedRepository.findFeedByIdAndDeleted(id, false))==null){
            throw new NullPointerException();
        }

        if (feed.getWriter().getId().equals(Long.parseLong(userInfo[0]))){
            throw new IllegalAccessException();
        }

        if (friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                PersonId.create(Long.parseLong(userInfo[0])), PersonId.create(feed.getWriter().getId()))==null){
            throw new IllegalStateException();
        }


        if (preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonIdAndLocation(feed.getId(), Long.parseLong(userInfo[0]), PreferenceLocation.FEED)==null){
            PreferenceInfo preferenceInfo = PreferenceInfo.create(Long.parseLong(userInfo[0]), feed.getId(), PreferenceLocation.FEED);
            preferenceInfo.locate(PreferenceLocation.FEED);
            preferenceInfo.like();
            preferenceInfoRepository.save(preferenceInfo);

            feed.like();

        }

        PreferenceInfo preferenceInfo
                    = preferenceInfoRepository
                    .findByDocumentIdAndPreferredPersonIdAndLocation(feed.getId(), Long.parseLong(userInfo[0]), PreferenceLocation.FEED);

        if (preferenceInfo.findState()==PreferenceStatus.LIKE){
                preferenceInfo.disLike();
                feed.disLike();

        }

        if (preferenceInfo.findState()==PreferenceStatus.INDIFFERENCE){
                preferenceInfo.like();
                feed.like();
        }

    }

    @Transactional
    public void likeCommentByFeedIdAndCommentId(Long feedId, Long commentId) throws AccessDeniedException {
        String[] userInfo = userService.findUserInfo();
        Feed feed;
        Comment comment;
        PreferenceInfo preferenceInfo;

        if ((feed=feedRepository.findFeedByIdAndDeleted(feedId, false))==null){
            throw new NullPointerException();
        }

        if (friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                PersonId.create(Long.parseLong(userInfo[0])), PersonId.create(feed.getWriter().getId()))==null){
            throw new IllegalStateException();
        }

        if((comment = commentService.findCommentById(feed.getComments(), commentId))==null){
            throw new NullPointerException();
        }

        if ((preferenceInfo = preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonIdAndLocation(feed.getId(), Long.parseLong(userInfo[0]), PreferenceLocation.COMMENT))==null){
            preferenceInfo = PreferenceInfo.create(Long.parseLong(userInfo[0]), feed.getId(), PreferenceLocation.COMMENT);
            preferenceInfo.locate(PreferenceLocation.COMMENT);
            preferenceInfo.like();
            preferenceInfoRepository.save(preferenceInfo);

            feed.like();



        }


        if (preferenceInfo.findState()==PreferenceStatus.LIKE){
            preferenceInfo.disLike();
            comment.disLike();

        }

        if (preferenceInfo.findState()==PreferenceStatus.INDIFFERENCE){
            preferenceInfo.like();
            comment.like();
        }



    }


}
