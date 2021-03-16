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
        User user = userRepository.findByUsername(username);
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
    public String dealWithFriendShip(Long friendId) {
        User user = null;

        User friendUser = userRepository.findUserById(friendId);

        Friendship friendship = friendShipRepository.findFriendshipByUserAndFriend_FriendId(user, friendId);

        if (friendship==null){
            
            Friendship forwordFriendship = createFriendship(friendUser, user);

            Friendship BackwordFriendship = createFriendship(user, friendUser);

            friendShipRepository.save(BackwordFriendship);
            
            friendShipRepository.save(forwordFriendship);

            return "Try to make FriendShip";

        }else if(friendship.getFriendState()){

            return "Already accept";

        }else {
            Friendship friendsFriendShip = friendShipRepository
                    .findFriendshipByUserAndFriend_FriendId(friendUser, user.getId());

            friendship.acceptFriendShip();
            friendsFriendShip.acceptFriendShip();
            
            return "Accept";
            
        }
    }

    private Friendship createFriendship(User user, User friendUser) {
        FriendId friendsId = new FriendId(friendUser.getId());
        FriendName friendName = new FriendName(friendUser.getUsername().getUsername());

        Friend friend = Friend.createFriend(friendsId, friendName);
        
        return Friendship.create(user, friend);
        
    }


}