package com.example.demo.feed.controller;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.RequestResult;
import com.example.demo.feed.service.FeedDto;
import com.example.demo.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;


@RestController
@RequiredArgsConstructor

public class FeedController {

    private final FeedService feedService;


    @GetMapping("/feeds/{feedId}")
    public Object findFeedDetailByFeedId(@PathVariable Long feedId) throws AccessDeniedException {
        Object result =  feedService.findFeedDetailByFeedId(feedId);
        if (result instanceof FeedDto){
            ResponseEntity.ok();
            return result;
        }else {
            switch ((RequestResult)result){
                case BAD_REQUEST:
                    ResponseEntity.status(HttpStatus.BAD_REQUEST);
                    break;
                case UNAUTHORIZED:
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED);
                    break;
            }
            return result;
        }
    }


    @PostMapping("/feeds")
    public Object makeFeed(@RequestParam String title,
                           @RequestParam String contents) throws AccessDeniedException {
        Object result = feedService.makeFeedByContents(title, contents);

        if (result instanceof FeedDto){
            ResponseEntity.ok();
            return result;
        }else {
            switch ((RequestResult)result){
                case BAD_REQUEST:
                    ResponseEntity.status(HttpStatus.BAD_REQUEST);
                    break;
            }
            return result;
        }

    }

    @DeleteMapping("/feeds/{feedId}")
    public void deleteFeedByFeedId(@PathVariable Long feedId) throws AccessDeniedException {
        RequestResult result = feedService.deleteFeedByFeedId(feedId);

        switch (result){
            case OK:
                ResponseEntity.ok();
                break;
            case BAD_REQUEST:
                ResponseEntity.status(HttpStatus.BAD_REQUEST);
                break;
            case FORBIDDEN:
                ResponseEntity.status(HttpStatus.FORBIDDEN);
                break;
            case UNAUTHORIZED:
                ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }

    }




}
