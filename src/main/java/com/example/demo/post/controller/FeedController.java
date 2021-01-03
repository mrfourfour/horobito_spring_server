package com.example.demo.post.controller;


import com.example.demo.post.domain.Feed;
import com.example.demo.post.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds")
    public Feed findTimeline(@RequestParam Long page,
                             @RequestParam Long pageSize){

    }

    @GetMapping("/feeds/{feedID}")
    public Feed  findFeedDetailByFeedId(@PathVariable Long id){

    }

    @PostMapping("/feeds")
    public void makeFeed(@RequestParam String contents){

    }

    @DeleteMapping("/feeds/{feedID}")
    public void deleteFeedByFeedId(@PathVariable Long id){}

    @PostMapping("/feeds/{feedId}/likes")
    public void likeFeedByFeedId(@PathVariable Long id){}


}
