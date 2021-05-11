package com.example.demo.feed.controller;


import com.example.demo.feed.service.CommentDto;
import com.example.demo.feed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/feed/{feedId}/comments")
    public void makeComment(@PathVariable Long feedId,
                            @RequestBody String contents) throws AccessDeniedException, IllegalAccessException {

        try {
            CommentDto result = commentService.makeCommentByFeedIdAndContents(feedId, contents);
            ResponseEntity.ok().body(result);
        }catch (IllegalArgumentException e){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);

        }catch (IllegalAccessException e){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);

        }


    }


}
