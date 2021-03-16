package com.example.demo.feed.controller;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor

public class FeedController {

    private final FeedService feedService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/feeds")
    public Page<Feed> findTimeline(@RequestParam int page,
                                   @RequestParam int pageSize){
        return feedService.findMyTimeLine(page, pageSize);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/feeds/{feedID}")
    public Feed  findFeedDetailByFeedId(@PathVariable Long FeedId){
        return feedService.findFeedDetailByFeedId(FeedId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/feeds")
    public void makeFeed(@RequestParam String contents){
        feedService.makeFeedByContents(contents);

    }

    @DeleteMapping("/feeds/{feedID}")
    public void deleteFeedByFeedId(@PathVariable Long id){
        feedService.deleteFeedByFeedId(id);

    }

    @PostMapping("/feeds/{feedId}/likes")
    public void likeFeedByFeedId(@PathVariable Long id){}


}
