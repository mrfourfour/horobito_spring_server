package com.example.demo.preferredperson.service;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.preferredperson.domain.PreferredPerson;
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


    @Transactional
    public void likeFeedByFeedID(Long id) throws AccessDeniedException {
        User user = userSessionService.getLoginedUser();
        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        if (preferredPersonRepository
                .findByDocumentIdAndPreferredPersonId(feed.getId(), user.getId())==null){

        }else {
            PreferredPerson preferredPerson
                    = preferredPersonRepository
                    .findByDocumentIdAndPreferredPersonId(feed.getId(), user.getId());



        }

        feed.like();
    }


}
