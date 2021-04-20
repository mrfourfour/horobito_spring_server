package com.example.demo.friend.service;


import com.example.demo.friend.domain.*;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendShipFindTest {

    @Mock
    FriendShipRepository friendShipRepository;



    @Mock
    UserService userService;

    @DisplayName("친구관계 요청 테스트  ")
    @Test
    void testForAccept() throws AccessDeniedException {

        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
userService);

        //given

        String[] person1Info = { "1", "friender1"};
        String [] person2Info = { "2", "friender2"};
        String [] person3Info = {"3", "friendee1"};
        String [] person4Info = {"4", "friendee2"};

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

        Friendship friendship1 = Friendship.create(friender4, friendee1);
        Friendship friendship2 = Friendship.create(friender3, friendee2);
        Friendship friendship3 = Friendship.create(friender2, friendee2);
        Friendship friendship4 = Friendship.create(friender1, friendee3);




        //when
        // 4,3,2 는 1에 친구 요청
        // 1은 3만 친구 요청

        when(userService.findUserInfo())
                .thenReturn(person1Info);

        friendShipRepository.save(friendship1);
        friendShipRepository.save(friendship2);
        friendShipRepository.save(friendship3);
        friendShipRepository.save(friendship4);

        Page<Friendship> dtos = friendShipRepository
                .findAllByFriendee_FriendeeId(
                       person1Id, PageRequest.of(0, 4));


        System.out.println();

        //then
        List<FriendDto> result = friendShipService.findRequestForMe(0, 4);
        for (FriendDto dtoss : result){
            System.out.println(dtos);
        }


    }

    private FriendDto toFriendDto(BasicInfo basicInfo) {
        return new FriendDto(
                basicInfo.getId(),
                basicInfo.getName()
        );
    }

}
