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


        Friendship friendship;;

        if ((friendship=friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId))==null){
            Friendship forwardFriendShip = createFriendship(myInfo, friendInfo);
            forwardFriendShip.acceptFriendShip();
            Friendship backwardFriendShip = createFriendship(friendInfo, myInfo);

            friendShipRepository.save(forwardFriendShip);
            friendShipRepository.save(backwardFriendShip);

            return FriendShipResult.TRY_TO_MAKE_FRIENDSHIP;



        }else {
            Friendship forwardFriendShip = friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId);
            Friendship backwardFriendShip = friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(friendInfo, myId);

            if (forwardFriendShip.getFriendState()){
                return FriendShipResult.ALREADY_ACCEPT;
            }else if (!forwardFriendShip.getFriendState() && backwardFriendShip.getFriendState()){
                forwardFriendShip.acceptFriendShip();
                return FriendShipResult.SUCCESS;
            }else {
                return FriendShipResult.DENIED;
            }

        }
    }

    private Friendship createFriendship(UserInfo user, UserInfo friendUserInfo) {
        Identfication friendsId = Identfication.create(friendUserInfo.getId());
        Name friendName = Name.create(friendUserInfo.getName());

        Friend friend = Friend.create(friendsId, friendName);
        
        return Friendship.create(user, friend);
    }


    public FriendShipResult deleteFriendShipRequest(Long inputedId) {
        User user = null;

        Name myName = Name.create(user.getUserBasicInfo().getUsername());
        Identfication myId = Identfication.create(user.getId());
        UserInfo myInfo = UserInfo.create(myId, myName);

        User friend = userRepository.findUserById(inputedId);

        Identfication friendId = Identfication.create(inputedId);
        Name friendName = Name.create(friend.getUserBasicInfo().getUsername());
        UserInfo friendInfo = UserInfo.create(friendId, friendName);

        Friendship friendship;;

        if ((friendship=friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId))==null){

            return FriendShipResult.NEVER_REQUESTED;

        }else {
            Friendship forwardFriendShip = friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId);
            forwardFriendShip.deleteFriendShip();

            return FriendShipResult.SUCCESS;
        }

    }

    public List<FriendDto>findRequestForMe(int page, int size) {
        User user = null;

        Name myName = Name.create(user.getUserBasicInfo().getUsername());
        Identfication myId = Identfication.create(user.getId());
        UserInfo myInfo = UserInfo.create(myId, myName);

        List<FriendDto> friendshipList = friendShipRepository.findAllByFriend_FriendId(myId, PageRequest.of(page, size))
                .stream()
                .filter(Friendship::getFriendState)
                .map(Friendship::getUserInfo)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }

    private FriendDto toFriendDto(BasicInfo basicInfo) {
        return new FriendDto(
                basicInfo.getId(),
                basicInfo.getName()
        );
    }

}
