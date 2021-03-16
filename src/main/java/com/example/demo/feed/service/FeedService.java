package com.example.demo.feed.service;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.domain.Writer;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteFeedByFeedId(Long id) {
        Feed feed = feedRepository.findFeedByIdAndIsDeleted(id, false);
        feed.delete();
    }



    public Page<Feed> findMyTimeLine(int page, int pageSize) { // 친구 문제
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserId(authentication.getName());

        Page<Feed> feeds = feedRepository.findAll(PageRequest.of(page, pageSize));
//        Page<Feed> friends = feedRepository.findAllByWriterId( user.getIsFriend().getFriendList().keySet());
        //// 여기서 막힘
        

        return feeds;
    }

    public Feed findFeedDetailByFeedId(Long id) {
        Feed feed = feedRepository.findFeedByIdAndIsDeleted(id, false);
        return feed;
    }



    public void makeFeedByContents(String contents) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserId(authentication.getName());
        Writer writer = Writer.makeWriter(user);
        Feed feed = Feed.createFeed(writer, contents);
        feedRepository.save(feed);
    }
}
