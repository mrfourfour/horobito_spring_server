package com.example.demo.post.controller;


import com.example.demo.post.domain.Feed;
import com.example.demo.post.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds")
    public List<Feed> findTimeline(@RequestParam int page,
                                   @RequestParam int pageSize){
        return feedService.findMyTimeLine(page, pageSize);

    }

    @GetMapping("/feeds/{feedID}")
    public Feed  findFeedDetailByFeedId(@PathVariable Long FeedId){
        feedService.findFeedDetailByFeedId(FeedId);

    }

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
