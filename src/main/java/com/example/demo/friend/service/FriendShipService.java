package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
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
    private final UserRepository userRepository;


    @Transactional
    public List<FriendDto> getMyFriends(int page, int size) {
        User user = null;
        Name userName = Name.create(user.getUserBasicInfo().getUsername());
        Identfication userId = Identfication.create(user.getId());
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
        User user = null;

        Name myName = Name.create(user.getUserBasicInfo().getUsername());
        Identfication myId = Identfication.create(user.getId());
        UserInfo myInfo = UserInfo.create(myId, myName);

        User friend = userRepository.findUserById(inputedFriendId);

        Identfication friendId = Identfication.create(inputedFriendId);
        Name friendName = Name.create(friend.getUserBasicInfo().getUsername());
        UserInfo friendInfo = UserInfo.create(friendId, friendName);


        Friendship friendship = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(myInfo, friendId);

        if (friendship==null){
            Friendship forwardFriendShip = createFriendship(myInfo, friendInfo);
            Friendship backwardFriendShip = createFriendship(friendInfo, myInfo);

            friendShipRepository.save(forwardFriendShip);
            friendShipRepository.save(backwardFriendShip);

            return FriendShipResult.Try_to_make_FriendShip;


        }else if(friendship.getFriendState()){


            return FriendShipResult.Already_Accept;
        }else {

            return FriendShipResult.Accept;

        }
    }

    private Friendship createFriendship(UserInfo user, UserInfo friendUserInfo) {
        Identfication friendsId = Identfication.create(friendUserInfo.getId());
        Name friendName = Name.create(friendUserInfo.getUsername());

        Friend friend = Friend.create(friendsId, friendName);
        
        return Friendship.create(user, friend);
    }


    public void deleteFriendShipRequest(Long inputedId) {
        User user = null;

        Name myName = Name.create(user.getUserBasicInfo().getUsername());
        Identfication myId = Identfication.create(user.getId());
        UserInfo myInfo = UserInfo.create(myId, myName);

        User friend = userRepository.findUserById(inputedId);

        Identfication friendId = Identfication.create(inputedId);
        Name friendName = Name.create(friend.getUserBasicInfo().getUsername());
        UserInfo friendInfo = UserInfo.create(friendId, friendName);

        Friendship forwardFriendShip
                = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(myInfo, friendId);

        Friendship backwardFriendShip
                = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(friendInfo, myId);

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
