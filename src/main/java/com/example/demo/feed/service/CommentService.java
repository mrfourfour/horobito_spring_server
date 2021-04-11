package com.example.demo.feed.service;


import com.example.demo.feed.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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


        Content content = Content.create(insertedContent);

        WriterId id = WriterId.create(user.getId());
        WriterName wrtName = WriterName.create(user.getUserBasicInfo().getUsername());
        Writer writer = Writer.create(id, wrtName);

        Comment comment = Comment.create(writer, content);
        feed.enrollComment(comment);
    }

    public Comment findCommentById(List<Comment> commentList, Long commentId){
        Comment result = null;
        for (Comment comment : commentList){
            if (comment.getId().equals(commentId)){
                return comment;

            }
        }
        return result;
    }

//    @Transactional
//    public String likeOrDislikeCommentByFeedIdAndCommentId(Long feedId, int commentId) {
//        Username username = Username.create("jihwan");
//        User user = userRepository.findByUserBasicInfo_Username(username);
//        try {
//            Feed feed = feedRepository.findFeedByIdAndDeleted(feedId, false);
//            Comment comment = feed.getComment(commentId);
//            if(comment.checkPossibleOfLike(user)){
//                comment.likeOrDislike();
//            }
//        } catch (Exception e){
//
//        }
//        return null;
//    }
}
