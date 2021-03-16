package com.example.demo.post.controller;



import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {


    @GetMapping
    public BoardResponse getBoards(){
        return new BoardResponse(Collections.emptyList());
    }

    @Value
    private static class BoardResponse {
        List<String> boards;
    }

}


//    private final FeedService feedService;



//    @GetMapping("/feeds")
//    public List<Feed> findTimeline(@RequestParam int page,
//                                   @RequestParam int pageSize){
//        return feedService.findMyTimeLine(page, pageSize);
//
//    }
//
//    @GetMapping("/feeds/{feedID}")
//    public Feed findFeedDetailByFeedId(@PathVariable Long FeedId){
//        return feedService.findFeedDetailByFeedId(FeedId);
//    }
//
//    @PostMapping("/feeds")
//    public void makeFeed(@RequestParam String contents){
//        feedService.makeFeedByContents(contents);
//
//    }
//
//    @DeleteMapping("/feeds/{feedID}")
//    public void deleteFeedByFeedId(@PathVariable Long id){
//        feedService.deleteFeedByFeedId(id);
//
//    }
//
//    @PostMapping("/feeds/{feedId}/likes")
//    public void likeFeedByFeedId(@PathVariable Long id){}


