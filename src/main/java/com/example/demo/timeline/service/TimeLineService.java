package com.example.demo.timeline.service;


import com.example.demo.feed.domain.Feed;
import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.feed.domain.Writer;
import com.example.demo.feed.domain.WriterId;
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

    public Feed[] findMyTimeLine(int page, int pageSize){
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

        Feed[] myTimeLine = (Feed[]) feedRepository.findAllByWriter_IdIn(writerList, PageRequest.of(page, pageSize))
                .stream().toArray();

        return myTimeLine;
    }

    private UserInfo createUserInfo(User user, Identfication id) {

        Name name = Name.create(user.getUserBasicInfo().getUsername());
        return UserInfo.create(id, name);
    }

}
