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
        Identfication userId = Identfication.create(user.getId());
        UserInfo userInfo = createUserInfo(user, userId);


        List<FriendDto> friendshipList
                = friendShipRepository
                .findAllByUserInfo(userInfo, PageRequest.of(page, size))
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
        Identfication myId = Identfication.create(user.getId());
        UserInfo myInfo = createUserInfo(user, myId);

        User friend = userRepository.findUserById(inputedFriendId);
        Identfication friendId = Identfication.create(inputedFriendId);
        UserInfo friendInfo = createUserInfo(friend, friendId);


        Friendship friendship;;

        if ((friendship=friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId))==null){
            Friendship forwardFriendShip = createFriendship(myInfo, friendInfo);
            forwardFriendShip.acceptFriendShip();

            Friendship backwardFriendShip = createFriendship(friendInfo, myInfo);

            friendShipRepository.save(forwardFriendShip);
            friendShipRepository.save(backwardFriendShip);

            return FriendShipResult.TRY_TO_MAKE_FRIENDSHIP;



        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId);
            Friendship backwardFriendShip
                    = friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(friendInfo, myId);

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
        Identfication myId = Identfication.create(user.getId());
        UserInfo myInfo = createUserInfo(user, myId);

        Identfication friendId = Identfication.create(inputedId);

        if ((friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId))==null){

            return FriendShipResult.NEVER_REQUESTED;

        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByUserInfoAndFriend_FriendId(myInfo, friendId);
            forwardFriendShip.deleteFriendShip();

            return FriendShipResult.SUCCESS;
        }

    }

    private UserInfo createUserInfo(User user, Identfication id) {

        Name name = Name.create(user.getUserBasicInfo().getUsername());
        return UserInfo.create(id, name);
    }


    public List<FriendDto>findRequestForMe(int page, int size) {

        User user = null;
        Identfication myId = Identfication.create(user.getId());

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
