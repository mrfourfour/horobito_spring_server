package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class FriendShipService {

    private final FriendShipRepository friendShipRepository;


    @Transactional
    public List<FriendDto> getMyFriends(int page, int size) {
        Name userName = null;
        Long id = Long.parseLong("3");
        Identfication userId = Identfication.create(id);
        UserInfo userInfo = UserInfo.create(userId, userName);

        List<FriendDto> friendshipList = friendShipRepository.findAllByUserInfo(userInfo, PageRequest.of(page, size))
                .stream()
                .filter(Friendship::getFriendState)
                .map(Friendship::getFriend)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }

    private FriendDto toFriendDto(Friend friend) {
        return new FriendDto(
                friend.getFriendId(),
                friend.getFriendName()
        );
    }


    @Transactional
    public FriendShipResult create(Long inputedFriendId) {

        UserInfo myInfo = UserInfo.create();
        Identfication myId = Identfication.create(myInfo.getId());
        Name myName = Name.create();



        UserInfo friendInfo = UserInfo.create();



        Friendship friendship = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId();

        if (friendship==null){


        }else if(friendship.getFriendState()){

            return FriendShipResult.Already_Accept;

        }else {

        }
    }

    private Friendship createFriendship(UserInfo user, UserInfo friendUserInfo) {
        Identfication friendsId = Identfication.create(friendUserInfo.getId());
        Name friendName = null;

        Friend friend = Friend.create(friendsId, friendName);
        
        return Friendship.create(user, friend);
    }


    public void deleteFriendShipRequest(Long inputId) {
        UserInfo me = null;

        UserInfo friendUser = null;
        Identfication friendId = Identfication.create(inputId);

        Identfication myId = Identfication.create(me.getId());

        Friendship forwardFriendShip
                = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(me, friendId);

        Friendship backwardFriendShip
                = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(friendUser, myId);

        forwardFriendShip.deleteFriendShip();
        backwardFriendShip.deleteFriendShip();

    }

    public List<FriendDto>findRequestForMe(int page, int size) {
        Name username = null;
        UserInfo user = null;
        List<FriendDto> friendshipList = friendShipRepository.findAllByUserInfo(user, PageRequest.of(page, size))
                .stream()
                .filter(friendship -> !friendship.getFriendState())
                .map(Friendship::getFriend)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }
}
