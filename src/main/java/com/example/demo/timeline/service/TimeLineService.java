package com.example.demo.timeline.service;


import com.example.demo.feed.domain.*;
import com.example.demo.feed.service.CommentDto;
import com.example.demo.feed.service.FeedDto;
import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeLineService {
    private final FriendShipRepository friendShipRepository;

    private final FeedRepository feedRepository;

    private final UserService userService;

    public List<FeedDto> findMyTimeLine(int page, int pageSize) throws AccessDeniedException {
        if (page<0 || pageSize<0){
            throw new IllegalArgumentException();
        }

        String [] userInfo = userService.findUserInfo();
        PersonId myId = PersonId.create(Long.parseLong(userInfo[0]));
        PersonName myName = PersonName.create(userInfo[1]);
        Friender user = Friender.create(myId, myName);

        List<WriterId> writerList =
                 friendShipRepository
                         .findAllByFriender(user)
                         .stream()
                         .filter(friendship -> friendship.getFriendState()==FriendShipState.ACCEPT)
                         .map(Friendship::getFriendeeId)
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
                ,feed.getPreferenceCountInfo().getPreference()
                ,feed.getWrtTime()
        );
    }

    public CommentDto toCommentDto(Comment comment){
        return new CommentDto(
                comment.getId()
                ,comment.getWriter().getId()
                ,comment.getWriter().getName()
                ,comment.getContent()
                ,comment.getPreferenceCountInfo().getPreference()
                ,comment.getWrtTime()
        );
    }


}
