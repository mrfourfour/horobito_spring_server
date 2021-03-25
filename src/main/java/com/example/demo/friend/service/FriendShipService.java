package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
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
        UserName userName = UserName.create("jihwan");
        Long id = Long.parseLong("3");
        UserId userId = UserId.create(id);
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
                friend.getFriendId().getFriendId(),
                friend.getFriendname().getFriendName()
        );
    }


    @Transactional
    public FriendShipResult create(Long inputId) {
        UserName username = UserName.create("jihwan");

        UserInfo user = null;
        FriendId myId = FriendId.create(inputId);

        UserInfo friendUser = null;
        FriendId friendId = FriendId.create(inputId);

        Friendship friendship = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(user, friendId);

        if (friendship==null){
            
            Friendship forwardFriendship =createFriendship(user, friendUser);
            Friendship backwardFriendship = createFriendship(friendUser, user);

            friendShipRepository.save(forwardFriendship); // 저장할 때 터진다.
            friendShipRepository.save(backwardFriendship);

            return FriendShipResult.Try_to_make_FriendShip;

        }else if(friendship.getFriendState()){

            return FriendShipResult.Already_Accept;

        }else {
            Friendship friendsFriendShip = friendShipRepository
                    .findFriendshipByUserInfoAndFriendAndFriend_FriendId(friendUser, myId);

            friendship.acceptFriendShip();
            friendsFriendShip.acceptFriendShip();
            
            return FriendShipResult.Accept;
            
        }
    }

    private Friendship createFriendship(UserInfo user, UserInfo friendUserInfo) {
        FriendId friendsId = FriendId.create(friendUserInfo.getId());
        FriendName friendName = null;

        Friend friend = Friend.create(friendsId, friendName);
        
        return Friendship.create(user, friend);
    }


    public void deleteFriendShipRequest(Long inputId) {
        UserInfo me = null;

        UserInfo friendUser = null;
        FriendId friendId = FriendId.create(inputId);

        FriendId myId = FriendId.create(me.getId());

        Friendship forwardFriendShip
                = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(me, friendId);

        Friendship backwardFriendShip
                = friendShipRepository.findFriendshipByUserInfoAndFriendAndFriend_FriendId(friendUser, myId);

        forwardFriendShip.deleteFriendShip();
        backwardFriendShip.deleteFriendShip();

    }

    public List<FriendDto>findRequestForMe(int page, int size) {
        UserName username = UserName.create("jihwan");
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
