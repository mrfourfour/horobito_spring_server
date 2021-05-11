package com.example.demo.feed.service;


import lombok.Value;

import java.time.Instant;

@Value
public class CommentDto {
    Long id;
    Long writerId;
    String writerName;
    String content;
    long preference;
    Instant createAt;

}
