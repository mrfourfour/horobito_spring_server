package com.example.demo.preperredPerson.service;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferredPersonService {

    private final FeedRepository feedRepository;

    @Transactional
    public void likeFeedByFeedID(Long id) {
        Feed feed = feedRepository.findFeedByIdAndDeleted(id, false);
        feed.like();
    }


}
