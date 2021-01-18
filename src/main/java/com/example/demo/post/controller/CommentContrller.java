package com.example.demo.post.controller;


import com.example.demo.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentContrller {

    private final CommentService commentService;


    @PostMapping("/feed/{feedId}/comments")
    public void makeComment(@PathVariable Long feedId,
                            @RequestParam String contents){
        commentService.makeCommentByFeedIdAndContents(feedId, contents);
    }

    @PostMapping("/feed/{feedId}/comments/{commentId}/like")
    public void likeOrDislikeCommentById(@PathVariable Long feedId,
                                         @PathVariable int commentId){
        commentService.likeOrDislikeCommentByFeedIdAndCommentId(feedId, commentId);
    }

}
