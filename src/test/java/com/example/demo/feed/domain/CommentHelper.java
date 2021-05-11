package com.example.demo.feed.domain;

public class CommentHelper {

    public static Comment create(Long id, Writer writer, Content content){
        Comment comment = Comment.create( writer, content);
        comment.setId(id);

        return comment;


    }
}
