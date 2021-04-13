package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
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
    private final UserSessionService userSessionService;
    private final UserService userService;






    @Transactional
    public List<FriendDto> getMyFriends(int page, int size) throws AccessDeniedException {
        Object[] userInfo = userService.findUserInfo();
        PersonId userId = PersonId.create((Long)userInfo[0]);
        PersonName userName = PersonName.create((String)userInfo[1]);

        Friender friender = Friender.create(userId, userName);


        List<FriendDto> friendshipList
                = friendShipRepository
                .findAllByFriender(friender, PageRequest.of(page, size))
                .stream()
                .filter(Friendship::getFriendState)
                .map(Friendship::getFriendee)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }




    @Transactional
    public FriendShipResult create(Long inputedFriendId) throws AccessDeniedException {
        Object[] user = userService.findUserInfo();
        Object[] friend = userService.findUserInfo(inputedFriendId);

        PersonId myId = PersonId.create((Long) user[0]);
        PersonId friendId = PersonId.create((Long) friend[0]);

        PersonName myName = PersonName.create((String) user[1]);
        PersonName friendName = PersonName.create((String) friend[1]);

        Friender frienderMe = Friender.create(myId, myName);
        Friendee friendeeYou = Friendee.create(friendId, friendName);

        Friender frienderYou = Friender.create(friendId, friendName);
        Friendee friendeeMe = Friendee.create(myId, myName);



        if ((friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(frienderMe, friendId))==null){
            Friendship forwardFriendShip = createFriendship(frienderMe, friendeeYou);
            forwardFriendShip.acceptFriendShip();

            Friendship backwardFriendShip = createFriendship(frienderYou, friendeeMe);

            friendShipRepository.save(forwardFriendShip);
            friendShipRepository.save(backwardFriendShip);

            return FriendShipResult.TRY_TO_MAKE_FRIENDSHIP;



        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(frienderMe, friendId);
            Friendship backwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(frienderYou, myId);

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



    public Friendship createFriendship(Friender user, Friendee friendUserInfo) {
        PersonId friendsId = PersonId.create(friendUserInfo.getId());
        PersonName friendName = PersonName.create(friendUserInfo.getName());

        Friendee friend = Friendee.create(friendsId, friendName);
        
        return Friendship.create(user, friend);
    }


    public FriendShipResult deleteFriendShipRequest(Long inputedId) throws AccessDeniedException {
        Object[] userInfo = userService.findUserInfo();
        PersonId myId = PersonId.create((Long) userInfo[0]);
        PersonName myName = PersonName.create((String) userInfo[1]);
        Friender myInfo = Friender.create(myId, myName);

        PersonId friendId = PersonId.create(inputedId);

        if ((friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(myInfo, friendId))==null){

            return FriendShipResult.NEVER_REQUESTED;

        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(myInfo, friendId);
            forwardFriendShip.deleteFriendShip();

            return FriendShipResult.SUCCESS;
        }

    }



    public List<FriendDto>findRequestForMe(int page, int size) throws AccessDeniedException {

        User user = userSessionService.getLoggeddUser();
        PersonId myId = PersonId.create(user.getId());

        List<FriendDto> friendshipList = friendShipRepository.findAllByFriendee_FriendeeId(myId, PageRequest.of(page, size))
                .stream()
                .filter(Friendship::getFriendState)
                .map(Friendship::getFriender)
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
