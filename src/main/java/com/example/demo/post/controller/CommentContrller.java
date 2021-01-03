package com.example.demo.post.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentContrller {
    @PostMapping("/feed/{feedId}/comments")
    public void makeComment(@PathVariable Long id){

    }

    @PostMapping("/feed/{feedId}/comments/{commentId}/like")
    public void likeOrDislikeCommentById(@PathVariable Long feedId,
                                         @PathVariable Long commentId){}

}
