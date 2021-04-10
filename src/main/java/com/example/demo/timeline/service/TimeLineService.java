package com.example.demo.timeline.service;


import com.example.demo.feed.domain.*;
import com.example.demo.feed.service.CommentDto;
import com.example.demo.feed.service.FeedDto;
import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeLineService {
    private final FriendShipRepository friendShipRepository;

    private final FeedRepository feedRepository;

    public List<FeedDto> findMyTimeLine(int page, int pageSize){
        User user = null;
        Identfication userId = Identfication.create(user.getId());
        UserInfo userInfo = createUserInfo(user, userId);

        List<WriterId> writerList =
                 friendShipRepository
                         .findAllByUserInfo(userInfo)
                         .stream()
                         .map(Friendship::getFriendId)
                         .map(WriterId::create)
                         .collect(Collectors.toList());
        writerList.add(WriterId.create(user.getId()));

        List<FeedDto> myTimeLine = feedRepository.findAllByWriter_IdIn(writerList, PageRequest.of(page, pageSize))
                .stream().map(this::toFeedDto).collect(Collectors.toList());

        return myTimeLine;
    }

    public FeedDto toFeedDto(Feed feed){
        return new FeedDto(
                feed.getId()
                ,feed.getWriter().getId()
                ,feed.getWriter().getName()
                ,feed.getContent().getContent()
                ,feed.getComments().stream().map(this::toCommentDto).collect(Collectors.toList())
                ,feed.getPreferenceInfo().getPreference()
                ,feed.getWrtTime()
        );
    }

    public CommentDto toCommentDto(Comment comment){
        return new CommentDto(
                comment.getId()
                ,comment.getWriter().getId()
                ,comment.getWriter().getName()
                ,comment.getContent()
                ,comment.getPreferenceInfo().getPreference()
                ,comment.getWrtTime()
        );
    }

    private UserInfo createUserInfo(User user, Identfication id) {

        Name name = Name.create(user.getUserBasicInfo().getUsername());
        return UserInfo.create(id, name);
    }

}
