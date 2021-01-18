package com.example.demo.post.service;


import com.example.demo.post.domain.Comment;
import com.example.demo.post.domain.Feed;
import com.example.demo.post.domain.FeedRepository;
import com.example.demo.post.domain.Writer;
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
        Feed feed = feedRepository.findById(feedId, false);
        Writer writer = null;

        Comment comment = Comment.makeComment(writer, contents);
        feed.enrollComment(comment);
    }

    public void likeOrDislikeCommentByFeedIdAndCommentId(Long feedId, int commentId) {
        Feed feed = feedRepository.findById(feedId, false);
        Comment comment = feed.getComments().get(commentId);
        comment.likeOrDislike();
    }
}
