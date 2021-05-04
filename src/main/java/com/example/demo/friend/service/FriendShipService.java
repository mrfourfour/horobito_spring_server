package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
import com.example.demo.user.service.UserDto;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class FriendShipService {

    private final FriendShipRepository friendShipRepository;
    private final UserService userService;


    @Transactional
    public List<FriendDto> getMyFriends(int page, int size) throws AccessDeniedException {
        if (page<0 || size<0){
            throw new IllegalArgumentException();
        }

        UserDto userInfo = userService.findUserInfo();
        PersonId userId = PersonId.create(userInfo.getUserId());
        PersonName userName = PersonName.create(userInfo.getUsername());

        Friender friender = Friender.create(userId, userName);

        List<FriendDto> friendshipList
                = friendShipRepository
                .findAllByFriender(friender, PageRequest.of(page, size))
                .stream()
                .filter(friendship -> friendship.getFriendState()==FriendShipState.ACCEPT)
                .map(Friendship::getFriendee)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }




    @Transactional
    public void create(Long inputedFriendId) throws AccessDeniedException {
        UserDto userInfo = userService.findUserInfo();
        UserDto friendInfo = userService.findUserInfo(inputedFriendId);

        PersonId myId = PersonId.create(userInfo.getUserId() );
        PersonId friendId = PersonId.create(friendInfo.getUserId());

        PersonName myName = PersonName.create(userInfo.getUsername());
        PersonName friendName = PersonName.create( friendInfo.getUsername());

        Friender frienderMe = Friender.create(myId, myName);
        Friendee friendeeYou = Friendee.create(friendId, friendName);

        Friender frienderYou = Friender.create(friendId, friendName);
        Friendee friendeeMe = Friendee.create(myId, myName);


        if ((friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(frienderMe, friendId))==null){
            Friendship forwardFriendShip = createFriendship(frienderMe, friendeeYou);
            forwardFriendShip.requestFriendShip();

            Friendship backwardFriendShip = createFriendship(frienderYou, friendeeMe);

            friendShipRepository.save(forwardFriendShip);
            friendShipRepository.save(backwardFriendShip);

        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(frienderMe, friendId);
            Friendship backwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(frienderYou, myId);

            if (forwardFriendShip.getFriendState()==FriendShipState.ACCEPT
                    || forwardFriendShip.getFriendState()==FriendShipState.REQUEST){
                throw new IllegalStateException();
            }else if (forwardFriendShip.getFriendState()==FriendShipState.REQUESTED
                    && backwardFriendShip.getFriendState()==FriendShipState.REQUEST){
                forwardFriendShip.acceptFriendShip();
                backwardFriendShip.acceptFriendShip();
            }else {
                throw new IllegalStateException();
            }

        }
    }




    public FriendShipResult deleteFriendShipRequest(Long inputedId) throws AccessDeniedException {
        UserDto userInfo = userService.findUserInfo();
        PersonId myId = PersonId.create(userInfo.getUserId());
        PersonName myName = PersonName.create(userInfo.getUsername());
        Friender myInfo = Friender.create(myId, myName);

        PersonId friendId = PersonId.create(inputedId);

        if ((friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(myInfo, friendId))==null){

            return FriendShipResult.NEVER_REQUESTED;

        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(myInfo, friendId);
            forwardFriendShip.deleteFriendShip();

            Friendship backwardFriendShip
                    = friendShipRepository.findFriendshipByFriender_FrienderIdAndFriendee_FriendeeId(friendId, myId);
            backwardFriendShip.requestFriendShip();


            return FriendShipResult.SUCCESS;
        }

    }



    public  List<FriendDto> findRequestForMe(int page, int size) throws AccessDeniedException {
        if (page<0 || size<0){
            throw new IllegalArgumentException();
        }


        PersonId myId = PersonId.create(userService.findUserInfo().getUserId());

        List<FriendDto> friendshipList = friendShipRepository.findAllByFriendee_FriendeeId(myId, PageRequest.of(page, size))
                .stream()
                .filter(friendship -> friendship.getFriendState()==FriendShipState.REQUEST)
                .map(Friendship::getFriender)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }

    public Friendship createFriendship(Friender friender, Friendee friendee) {

        return Friendship.create(friender, friendee);
    }

    private FriendDto toFriendDto(BasicInfo basicInfo) {
        return new FriendDto(
                basicInfo.getId(),
                basicInfo.getName()
        );
    }

}
