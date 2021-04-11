package com.example.demo.feed.controller;


import com.example.demo.feed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/feed/{feedId}/comments")
    public void makeComment(@PathVariable Long feedId,
                            @RequestBody String contents) throws AccessDeniedException {
        commentService.makeCommentByFeedIdAndContents(feedId, contents);
    }

//    @PostMapping("/feed/{feedId}/comments/{commentId}/like")
//    public void likeOrDislikeCommentById(@PathVariable Long feedId,
//                                         @PathVariable int commentId){
//        commentService.likeOrDislikeCommentByFeedIdAndCommentId(feedId, commentId);
//    }

}
