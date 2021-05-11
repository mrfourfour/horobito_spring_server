package com.example.demo.friend.service;


import com.example.demo.friend.domain.*;
import com.example.demo.user.service.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FriendShipFindTest {

    @Mock
    FriendShipRepository friendShipRepository;




    @Mock
    UserService userService;

    // 성공
    @DisplayName("친구관계 요청 확인 테스트1. 알맞은 요청 테스트 || 잘못된 페이지 번호 또는 크기 ")
    @Test
    void testForAccept() throws AccessDeniedException {

        FriendShipService sut
                = new FriendShipService(friendShipRepository,
userService);

        //given

        String[] person1Info = { "1", "person1"};
        String [] person2Info = { "2", "person2"};
        String [] person3Info = {"3", "person3"};
        String [] person4Info = {"4", "person4"};

        PersonId person1Id = PersonId.create(Long.parseLong(person1Info[0]) );
        PersonId person2Id = PersonId.create(Long.parseLong( person2Info[0]));
        PersonId person3Id = PersonId.create(Long.parseLong(person3Info[0]));
        PersonId person4Id = PersonId.create(Long.parseLong(person4Info[0]));

        PersonName person1Name = PersonName.create( person1Info[1]);
        PersonName person2Name = PersonName.create( person2Info[1]);
        PersonName person3Name = PersonName.create(person3Info[1]);
        PersonName person4Name = PersonName.create(person4Info[1]);

        Friender friender1 = Friender.create(person1Id, person1Name);
        Friender friender2 = Friender.create(person2Id, person2Name);
        Friender friender3 = Friender.create(person3Id, person3Name);
        Friender friender4 = Friender.create(person4Id, person4Name);

        Friendee friendee1 = Friendee.create(person1Id, person1Name);
        Friendee friendee2 = Friendee.create(person2Id, person2Name);
        Friendee friendee3 = Friendee.create(person3Id, person3Name);
        Friendee friendee4 = Friendee.create(person4Id, person4Name);

        Friendship forwardFriendship1 = Friendship.create(friender4, friendee1);
        forwardFriendship1.requestFriendShip();

        Friendship forwardFriendship2 = Friendship.create(friender3, friendee1);
        forwardFriendship2.requestFriendShip();

        Friendship forwardFriendship3 = Friendship.create(friender2, friendee1);
        forwardFriendship3.requestFriendShip();

        Friendship forwardFriendship4 = Friendship.create(friender1, friendee3);
        forwardFriendship4.requestFriendShip();

        List<Friendship> forwardFriendshipList = new ArrayList<>();
        List<Friendship> backwardFriendshipList = new ArrayList<>();

        forwardFriendshipList.add(forwardFriendship1);
        forwardFriendshipList.add(forwardFriendship2);
        forwardFriendshipList.add(forwardFriendship3);

        Page<Friendship> forwardPage =  new PageImpl<>(forwardFriendshipList);

        //when
        // 4,3,2 는 1에 친구 요청
        // 1은 3만 친구 요청
        when(userService.findUserInfo()).thenReturn(person1Info);
        when(friendShipRepository
                .findAllByFriendee_FriendeeId(any(), any()))
                .thenReturn(forwardPage);


        //then
        assertThrows(IllegalArgumentException.class, ()->sut.findRequestForMe(-1, 2));
        assertThrows(IllegalArgumentException.class, ()->sut.findRequestForMe(1, -2));

        List<FriendDto> result = sut.findRequestForMe(0, 4);
        for (FriendDto dto : result){
                    System.out.println(dto);
        }


    }

    // 성공
    @DisplayName("친구관계 요청 확인 테스트2. 조건에 맞는 요청이 없는 경우 테스트  ")
    @Test
    void test2() throws AccessDeniedException {

        FriendShipService sut
                = new FriendShipService(friendShipRepository,
                userService);

        //given

        String[] person1Info = { "1", "person1"};
        String [] person2Info = { "2", "person2"};
        String [] person3Info = {"3", "person3"};
        String [] person4Info = {"4", "person4"};

        PersonId person1Id = PersonId.create(Long.parseLong(person1Info[0]) );
        PersonId person2Id = PersonId.create(Long.parseLong( person2Info[0]));
        PersonId person3Id = PersonId.create(Long.parseLong(person3Info[0]));
        PersonId person4Id = PersonId.create(Long.parseLong(person4Info[0]));

        PersonName person1Name = PersonName.create( person1Info[1]);
        PersonName person2Name = PersonName.create( person2Info[1]);
        PersonName person3Name = PersonName.create(person3Info[1]);
        PersonName person4Name = PersonName.create(person4Info[1]);

        Friender friender1 = Friender.create(person1Id, person1Name);
        Friender friender2 = Friender.create(person2Id, person2Name);
        Friender friender3 = Friender.create(person3Id, person3Name);
        Friender friender4 = Friender.create(person4Id, person4Name);

        Friendee friendee1 = Friendee.create(person1Id, person1Name);
        Friendee friendee2 = Friendee.create(person2Id, person2Name);
        Friendee friendee3 = Friendee.create(person3Id, person3Name);
        Friendee friendee4 = Friendee.create(person4Id, person4Name);


        Friendship backwardFriendship1 = Friendship.create(friender4, friendee1);
        backwardFriendship1.deleteFriendShip();

        Friendship backwardFriendship2 = Friendship.create(friender3, friendee1);
        backwardFriendship2.acceptFriendShip();

        Friendship backwardFriendship3 = Friendship.create(friender2, friendee1);
        backwardFriendship3.requestFriendShip();

        Friendship forwardFriendship4 = Friendship.create(friender1, friendee3);
        forwardFriendship4.requestFriendShip();


        List<Friendship> backwardFriendshipList = new ArrayList<>();

        backwardFriendshipList.add(backwardFriendship1);
        backwardFriendshipList.add(backwardFriendship2);
        backwardFriendshipList.add(backwardFriendship3);

        Page<Friendship> backwardPage = new PageImpl<>(backwardFriendshipList);

        //when
        // 4,3,2 는 1에 친구 요청
        // 1은 3만 친구 요청
        when(userService.findUserInfo()).thenReturn(person1Info);
        when(friendShipRepository
                .findAllByFriendee_FriendeeId(any(), any()))
                .thenReturn(backwardPage);


        //then
        assertThrows(IllegalArgumentException.class, ()->sut.findRequestForMe(-1, 2));
        assertThrows(IllegalArgumentException.class, ()->sut.findRequestForMe(1, -2));


        // 2번만 요청이 나와야 한다.
        List<FriendDto> result = sut.findRequestForMe(0, 4);
        for (FriendDto dto : result){
            System.out.println(dto);
        }


    }

    @DisplayName("친구목록 확인 테스트 1. 조건에 맞게 불러오는 것 테스트   ")
    @Test
    void testForSearchingFriendList1() throws AccessDeniedException {
        FriendShipService sut
                = new FriendShipService(friendShipRepository,
                userService);

        //given

        String[] person1Info = { "1", "person1"};
        String [] person2Info = { "2", "person2"};
        String [] person3Info = {"3", "person3"};
        String [] person4Info = {"4", "person4"};

        PersonId person1Id = PersonId.create(Long.parseLong(person1Info[0]) );
        PersonId person2Id = PersonId.create(Long.parseLong( person2Info[0]));
        PersonId person3Id = PersonId.create(Long.parseLong(person3Info[0]));
        PersonId person4Id = PersonId.create(Long.parseLong(person4Info[0]));

        PersonName person1Name = PersonName.create( person1Info[1]);
        PersonName person2Name = PersonName.create( person2Info[1]);
        PersonName person3Name = PersonName.create(person3Info[1]);
        PersonName person4Name = PersonName.create(person4Info[1]);

        Friender friender1 = Friender.create(person1Id, person1Name);
        Friender friender2 = Friender.create(person2Id, person2Name);
        Friender friender3 = Friender.create(person3Id, person3Name);
        Friender friender4 = Friender.create(person4Id, person4Name);

        Friendee friendee1 = Friendee.create(person1Id, person1Name);
        Friendee friendee2 = Friendee.create(person2Id, person2Name);
        Friendee friendee3 = Friendee.create(person3Id, person3Name);
        Friendee friendee4 = Friendee.create(person4Id, person4Name);

        Friendship forwardFriendship1 = Friendship.create(friender1, friendee4);
        forwardFriendship1.acceptFriendShip();

        Friendship forwardFriendship2 = Friendship.create(friender1, friendee2);
        forwardFriendship2.deleteFriendShip();

        Friendship forwardFriendship3 = Friendship.create(friender1, friendee3);
        forwardFriendship3.acceptFriendShip();


        List<Friendship> forwardFriendshipList = new ArrayList<>();

        forwardFriendshipList.add(forwardFriendship1);
        forwardFriendshipList.add(forwardFriendship2);
        forwardFriendshipList.add(forwardFriendship3);

        Page<Friendship> forwardPage =  new PageImpl<>(forwardFriendshipList);

        //when
        when(userService.findUserInfo()).thenReturn(person1Info);
        when(friendShipRepository
                .findAllByFriender(any(), any()))
                .thenReturn(forwardPage);


        //then
        assertThrows(IllegalArgumentException.class, ()->sut.getMyFriends(-1, 2));
        assertThrows(IllegalArgumentException.class, ()->sut.getMyFriends(1, -2));


        List<FriendDto> result = sut.getMyFriends(0, 4);
        for (FriendDto dto : result){
            System.out.println(dto);
        }

    }


}
