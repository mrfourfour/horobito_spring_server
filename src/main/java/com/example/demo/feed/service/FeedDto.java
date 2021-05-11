package com.example.demo.feed.service;


import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
public class FeedDto {

    Long id;
    Long writerId;
    String writerName;
    String content;
    List<CommentDto> comments;
    long preference;
    Instant createAt;



}
