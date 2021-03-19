package com.example.demo.feed.service;


import com.example.demo.feed.domain.Comment;
import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.domain.Writer;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Feed feed = feedRepository.findFeedByIdAndIsDeleted(feedId, false);
        Username username = Username.create(authentication.getName());
        User user = userRepository.findByUserBasicInfo_Username(username);
        Writer writer = Writer.create(user);

        Comment comment = Comment.create(writer, insertedContent);
        feed.enrollComment(comment);
    }

    @Transactional
    public String likeOrDislikeCommentByFeedIdAndCommentId(Long feedId, int commentId) {
        User user = null;
        try {
            Feed feed = feedRepository.findFeedByIdAndIsDeleted(feedId, false);
            Comment comment = feed.getComments().get(commentId);
            if(comment.checkPossibleOfLike(user)){
                comment.likeOrDislike();
            }
        } catch (Exception e){
            return "400 Bad Request";
        }
        return null;
    }
}
