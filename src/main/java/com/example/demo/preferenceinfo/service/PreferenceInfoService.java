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
import com.example.demo.user.service.UserDto;
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
        UserDto userInfo = userService.findUserInfo(); // dto 사용할 것
        Feed feed;

        // 변수는 final 처럼 작성이 깔끔
        // 변수의 초기화는 참조가 계속 바뀌기 때문에, 런타임에 이해 어려움
        // 초기화는 왠만해서는 딱 1번만

        if ((feed=feedRepository.findFeedByIdAndDeleted(id, false))==null){
            throw new NullPointerException();// 고칠 것 -> IllegalStateException() 등, 또는 IlliArgument xxx[id 값 오류]
        }

        if (feed.getWriter().getId().equals(userInfo.getUserId())){
            throw new IllegalAccessException();
        }

        if ((friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                PersonId.create(userInfo.getUserId()), PersonId.create(feed.getWriter().getId())))==null){
            throw new IllegalStateException();
        }

        PreferenceInfo preferenceInfo = preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonIdAndLocation(feed.getId(), userInfo.getUserId(), PreferenceLocation.FEED);



        // 비지니스 예외 방식에 예외처리를 담는 것은 좋은 방식이 아니다 . - 비쌈
        if (preferenceInfo==null){

            preferenceInfo = PreferenceInfo.create(userInfo.getUserId(), feed.getId());
            preferenceInfo.locate(PreferenceLocation.FEED);
            preferenceInfo.like();
            preferenceInfoRepository.save(preferenceInfo);

            feed.like();

        }else {
            // 변수 추출 : else 일때 2번
            if (preferenceInfo.findState()==PreferenceStatus.LIKE){
                preferenceInfo.disLike();
                feed.disLike();
            }else {
                if (preferenceInfo.findState()==PreferenceStatus.INDIFFERENCE){
                    preferenceInfo.like();
                    feed.like();
                }
            }


        }



    }

    @Transactional
    public void likeCommentByFeedIdAndCommentId(Long feedId, Long commentId) throws AccessDeniedException {
        UserDto userInfo = userService.findUserInfo();
        Feed feed = feedRepository.findFeedByIdAndDeleted(feedId, false);


        if (feed==null){
            throw new IllegalArgumentException();
        }

        Comment comment =commentService.findCommentById(feed.getComments(), commentId);

        if(comment ==null){
            throw new IllegalArgumentException();
        }

        if (friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(
                PersonId.create(userInfo.getUserId()), PersonId.create(feed.getWriter().getId()))==null){
            throw new IllegalStateException();
        }


        PreferenceInfo preferenceInfo = preferenceInfoRepository
                .findByDocumentIdAndPreferredPersonIdAndLocation(comment.getId(), userInfo.getUserId(), PreferenceLocation.COMMENT);

        if (preferenceInfo ==null){
            preferenceInfo = PreferenceInfo.create(userInfo.getUserId(), comment.getId());
            preferenceInfo.locate(PreferenceLocation.COMMENT);
            preferenceInfo.like();
            preferenceInfoRepository.save(preferenceInfo);

            comment.like();



        }else {
            if (preferenceInfo.findState()==PreferenceStatus.LIKE){
                preferenceInfo.disLike();
                comment.disLike();

            }else {
                if (preferenceInfo.findState()==PreferenceStatus.INDIFFERENCE){
                    preferenceInfo.like();
                    comment.like();
                }
            }


        }






    }


}
