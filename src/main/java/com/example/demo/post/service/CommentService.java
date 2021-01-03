package com.example.demo.post.service;


import com.example.demo.post.domain.FeedRepository;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final FeedRepository feedRepository;

    private final UserRepository userRepository;
}
