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
    @GetMapping("/feeds/{feedId}")
    public Feed  findFeedDetailByFeedId(@PathVariable Long feedId){
        return feedService.findFeedDetailByFeedId(feedId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/feeds")
    public void makeFeed(@RequestBody String contents){
        feedService.makeFeedByContents(contents);

    }

    @DeleteMapping("/feeds/{feedId}")
    public void deleteFeedByFeedId(@PathVariable Long feedId){
        feedService.deleteFeedByFeedId(feedId);

    }

    @PostMapping("/feeds/{feedId}/likes")
    public void likeFeedByFeedId(@PathVariable Long feedId){
        feedService.likeFeedByFeedID(feedId);
    }


}
