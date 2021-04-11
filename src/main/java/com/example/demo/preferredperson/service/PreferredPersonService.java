package com.example.demo.preferredperson.service;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.domain.WriterId;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.friend.domain.Identfication;
import com.example.demo.preferredperson.domain.PreferenceStatus;
import com.example.demo.preferredperson.domain.PreferredPerson;
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


    @Transactional
    public PreferenceResult likeFeedByFeedId(Long id) throws AccessDeniedException {
        User user = userSessionService.getLoginedUser();
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
            PreferredPerson preferredPerson = PreferredPerson.create(user.getId(), feed.getId(), PreferredPersonInfoLocation.FEED);
            preferredPerson.locate(PreferredPersonInfoLocation.FEED);
            preferredPerson.like();
            preferredPersonRepository.save(preferredPerson);

            feed.like();

            return PreferenceResult.SUCCESS;

        }

        PreferredPerson preferredPerson
                    = preferredPersonRepository
                    .findByDocumentIdAndPreferredPersonId(feed.getId(), user.getId());

        if (preferredPerson.findState()==PreferenceStatus.LIKE){
                preferredPerson.disLike();
                feed.disLike();

                return PreferenceResult.SUCCESS;
        }

        if (preferredPerson.findState()==PreferenceStatus.INDIFFERENCE){
                preferredPerson.like();
                feed.like();
        }
        return PreferenceResult.SUCCESS;

    }



}
