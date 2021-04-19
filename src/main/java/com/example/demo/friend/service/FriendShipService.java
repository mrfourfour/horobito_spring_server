package com.example.demo.friend.service;

import com.example.demo.friend.domain.*;
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

        String[] userInfo = userService.findUserInfo();
        PersonId userId = PersonId.create(Long.parseLong(userInfo[0]));
        PersonName userName = PersonName.create(userInfo[1]);

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
        String[] user = userService.findUserInfo();
        String [] friend = userService.findUserInfo(inputedFriendId);

        PersonId myId = PersonId.create(Long.parseLong(user[0]) );
        PersonId friendId = PersonId.create(Long.parseLong(friend[0]));

        PersonName myName = PersonName.create(user[1]);
        PersonName friendName = PersonName.create( friend[1]);

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
                return FriendShipResult.ALREADY_ACCEPT;
            }else if (forwardFriendShip.getFriendState()==FriendShipState.REQUESTED
                    && backwardFriendShip.getFriendState()==FriendShipState.REQUEST){
                forwardFriendShip.acceptFriendShip();
                backwardFriendShip.acceptFriendShip();
            }else {
                throw new               
                return FriendShipResult.DENIED;
            }

        }
    }




    public FriendShipResult deleteFriendShipRequest(Long inputedId) throws AccessDeniedException {
        String[] userInfo = userService.findUserInfo();
        PersonId myId = PersonId.create(Long.parseLong(userInfo[0]));
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



    public  Object findRequestForMe(int page, int size) throws AccessDeniedException {
        if (page<0 || size<0){
            return FriendShipResult.DENIED;
        }


        PersonId myId = PersonId.create(Long.parseLong(userService.findUserInfo()[0]));

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
