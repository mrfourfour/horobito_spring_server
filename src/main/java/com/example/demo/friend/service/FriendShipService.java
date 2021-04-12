package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
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
    private final UserRepository userRepository;
    private final UserSessionService userSessionService;


    @Transactional
    public List<FriendDto> getMyFriends(int page, int size) throws AccessDeniedException {
        User user = userSessionService.getLoggeddUser();
        PersonId userId = PersonId.create(user.getId());
        Friender userInfo = createUserInfo(user, userId);


        List<FriendDto> friendshipList
                = friendShipRepository
                .findAllByFriender(userInfo, PageRequest.of(page, size))
                .stream()
                .filter(Friendship::getFriendState)
                .map(Friendship::getFriendee)
                .map(this::toFriendDto)
                .collect(Collectors.toList());

        return friendshipList;
    }




    @Transactional
    public FriendShipResult create(Long inputedFriendId) throws AccessDeniedException {
        User user = userSessionService.getLoggeddUser();
        PersonId myId = PersonId.create(user.getId());
        Friender myInfo = createUserInfo(user, myId);

        User friend = userRepository.findUserById(inputedFriendId);
        PersonId friendId = PersonId.create(inputedFriendId);
        Friender friendInfo = createUserInfo(friend, friendId);




        if ((friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(myInfo, friendId))==null){
            Friendship forwardFriendShip = createFriendship(myInfo, friendInfo);
            forwardFriendShip.acceptFriendShip();

            Friendship backwardFriendShip = createFriendship(friendInfo, myInfo);

            friendShipRepository.save(forwardFriendShip);
            friendShipRepository.save(backwardFriendShip);

            return FriendShipResult.TRY_TO_MAKE_FRIENDSHIP;



        }else {
            Friendship forwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(myInfo, friendId);
            Friendship backwardFriendShip
                    = friendShipRepository.findFriendshipByFrienderAndFriendee_FriendeeId(friendInfo, myId);

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



    private Friendship createFriendship(Friender user, Friender friendUserInfo) {
        PersonId friendsId = PersonId.create(friendUserInfo.getId());
        PersonName friendName = PersonName.create(friendUserInfo.getName());

        Friendee friend = Friendee.create(friendsId, friendName);
        
        return Friendship.create(user, friend);
    }


    public FriendShipResult deleteFriendShipRequest(Long inputedId) throws AccessDeniedException {
        User user = userSessionService.getLoggeddUser();
        PersonId myId = PersonId.create(user.getId());
        Friender myInfo = createUserInfo(user, myId);

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

    private Friender createUserInfo(User user, PersonId id) {

        PersonName name = PersonName.create(user.getUserBasicInfo().getUsername());
        return Friender.create(id, name);
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
