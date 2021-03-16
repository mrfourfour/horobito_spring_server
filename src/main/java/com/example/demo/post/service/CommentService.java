package com.example.demo.post.service;

import com.example.demo.post.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final FeedRepository feedRepository;

    private final UserRepository userRepository;

    public void makeCommentByFeedIdAndContents(Long feedId, String contents) {
        User user = null;
        Feed feed = feedRepository.findByIdAndDeleted(feedId, false);
        Writer writer = null;
        Content content = Content.createContent(contents);

        Comment comment = Comment.makeComment(writer, content);
        feed.enrollComment(comment);
    }

    public void likeOrDislikeCommentByFeedIdAndCommentId(Long feedId, int commentId) {
        Feed feed = feedRepository.findByIdAndDeleted(feedId, false);
        Comment comment = feed.getComments().get(commentId);
    }
}