package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final FeedRepository feedRepository;

    private final UserRepository userRepository;

    @Transactional
    public void makeCommentByFeedIdAndContents(Long feedId, String insertedContent) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Feed feed = feedRepository.findFeedByIdAndDeleted(feedId, false);
//        Username username = Username.create(authentication.getName());
        Username username = Username.create("jihwan");


        User user = userRepository.findByUserBasicInfo_Username(username);
        Writer writer = Writer.create(user);
        Content content = Content.create(insertedContent);

        Comment comment = Comment.create(writer, content);
        feed.enrollComment(comment);
    }

    @Transactional
    public String likeOrDislikeCommentByFeedIdAndCommentId(Long feedId, int commentId) {
        Username username = Username.create("jihwan");
        User user = userRepository.findByUserBasicInfo_Username(username);
        try {
            Feed feed = feedRepository.findFeedByIdAndDeleted(feedId, false);
            Comment comment = feed.getComment(commentId);
            if(comment.checkPossibleOfLike(user)){
                comment.likeOrDislike();
            }
        } catch (Exception e){
            return "400 Bad Request";
        }
        return null;
    }
}
