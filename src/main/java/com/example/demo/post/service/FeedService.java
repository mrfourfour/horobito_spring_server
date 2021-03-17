package com.example.demo.post.service;

import com.example.demo.post.domain.Content;
import com.example.demo.post.domain.Feed;
import com.example.demo.post.domain.FeedRepository;
import com.example.demo.post.domain.Writer;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    @Transactional
    public void deleteFeedByFeedId(Long id) {
        Feed feed = feedRepository.findByIdAndDeleted(id, false);
        feed.delete();
    }

    public List<Feed> findMyTimeLine(int page, int pageSize) {
        Page<Feed> feeds = feedRepository.findAll(PageRequest.of(page, pageSize));
        return feeds.toList();
    }

    public Feed findFeedDetailByFeedId(Long id) {
        Feed feed = feedRepository.findByIdAndDeleted(id, false);
        return feed;
    }

    public void makeFeedByContents(String contents) {
        User user = null;
        Writer writer = new Writer(user.getId(), user.getUsername().getUsername());
        Content content = Content.createContent(contents);
        Feed feed = Feed.createFeed(writer, content);
    }
}
