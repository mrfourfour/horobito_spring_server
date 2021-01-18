package com.example.demo.post.service;


import com.example.demo.post.domain.Feed;
import com.example.demo.post.domain.FeedRepository;
import com.example.demo.post.domain.Writer;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteFeedByFeedId(Long id) {
        Feed feed = feedRepository.findById(id, false);
        feed.delete();
    }

    public List<Feed> findMyTimeLine(int page, int pageSize) {
        Page<Feed> feeds = feedRepository.findAll(PageRequest.of(page, pageSize));
        return feeds.toList();
    }

    public Feed findFeedDetailByFeedId(Long id) {
        Feed feed = feedRepository.findById(id, false);
        return feed;
    }

    public void makeFeedByContents(String contents) {
        User user = null;
        Writer writer = new Writer(user.getId(), user.getUserId());
        Feed feed = Feed.createFeed(writer, contents);
    }
}
