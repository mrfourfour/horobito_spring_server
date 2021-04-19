package com.example.demo.friend.service;


import com.example.demo.friend.domain.*;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.UserSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendShipFindTest {

    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    UserSessionService userSessionService;

    @Mock
    UserService userService;

    @DisplayName("친구관계 찾기 테스트  ")
    @Test
    void testForAccept() throws AccessDeniedException {

        FriendShipService friendShipService
                = new FriendShipService(friendShipRepository,
userService);

        //given

        String[] friender1Info = { "1", "friender1"};
        String [] friender2Info = { "2", "friender2"};
        String [] friendee1Info = {"3", "friendee1"};
        String [] friendee2Info = {"4", "friendee2"};

        PersonId friender1Id = PersonId.create(Long.parseLong(friender1Info[0]) );
        PersonId friender2Id = PersonId.create(Long.parseLong(friender2Info[0]));
        PersonId friendee1Id = PersonId.create(Long.parseLong(friendee1Info[0]));
        PersonId friendee2Id = PersonId.create(Long.parseLong(friendee2Info[0]));

        PersonName friender1Name = PersonName.create( friender1Info[1]);
        PersonName friender2Name = PersonName.create(friender2Info[1]);
        PersonName friendee1Name = PersonName.create(friendee1Info[1]);
        PersonName friendee2Name = PersonName.create(friendee2Info[1]);

        Friender friender1 = Friender.create(friender1Id, friender1Name);
        Friender friender2 = Friender.create(friender2Id, friender2Name);

        Friendee friendee1 = Friendee.create(friendee1Id, friendee1Name);
        Friendee friendee2 = Friendee.create(friendee2Id, friendee2Name);

        Friendship friendship1 = Friendship.create(friender1, friendee1);
        Friendship friendship2 = Friendship.create(friender2, friendee1);

        friendShipRepository.save(friendship1);
        friendShipRepository.save(friendship2);

        when(userService.findUserInfo())
                .thenReturn(friendee1Info);



        //when



        //then



    }

}
