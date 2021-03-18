package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
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
    public List<FriendDto> getMyFriendList(int page, int size) {
        Username username = Username.createUsername("jihwan");
        User user = userRepository.findByUserBasicInfo_Username(username);
        List<FriendDto> friendshipList = friendShipRepository.findAllByUser(user, PageRequest.of(page, size))
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
    public Result dealWithFriendShip(Long friendId) {
        User user = null;
        User friendUser = userRepository.findUserById(friendId);
        Friendship friendship = friendShipRepository.findFriendshipByUserAndFriend_FriendId(user, friendId);

        if (friendship==null){
            
            Friendship forwardFriendship = createFriendship(friendUser, user);
            Friendship backwardFriendship = createFriendship(user, friendUser);

            friendShipRepository.save(backwardFriendship);
            friendShipRepository.save(forwardFriendship);

            return Result.Try_to_make_FriendShip;

        }else if(friendship.getFriendState()){

            return Result.Already_Accept;

        }else {
            Friendship friendsFriendShip = friendShipRepository
                    .findFriendshipByUserAndFriend_FriendId(friendUser, user.getId());

            friendship.acceptFriendShip();
            friendsFriendShip.acceptFriendShip();
            
            return Result.Accept;
            
        }
    }

    private Friendship createFriendship(User user, User friendUser) {
        FriendId friendsId = FriendId.createFriendId(friendUser.getId());
        FriendName friendName = FriendName.createFriendName(friendUser.getUserBasicInfo().getUsernameInfo());

        Friend friend = Friend.createFriend(friendsId, friendName);
        
        return Friendship.create(user, friend);
        
    }


    public void deleteFriendShipRequest(Long friendId) {
        User me = null;

        User friendUser = userRepository.findUserById(friendId);

        Friendship forwardFriendShip
                = friendShipRepository.findFriendshipByUserAndFriend_FriendId(me, friendUser.getId());

        Friendship backwardFriendShip
                = friendShipRepository.findFriendshipByUserAndFriend_FriendId(friendUser, me.getId());

        forwardFriendShip.deleteFriendShip();
        backwardFriendShip.deleteFriendShip();

    }

    public List<FriendDto>findRequestForMe(int page, int size) {
        Username username = Username.createUsername("jihwan");
        User user = userRepository.findByUserBasicInfo_Username(username);
        List<FriendDto> friendshipList = friendShipRepository.findAllByUser(user, PageRequest.of(page, size))
                .stream()
                .filter(friendship -> !friendship.getFriendState())
                .map(Friendship::getFriend)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }
}
