package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserSessionService userSessionService;


    @Transactional
    public void deleteFeedByFeedId(Long id) {
        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        feed.delete();
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
