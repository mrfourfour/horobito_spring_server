package com.example.demo.timeline.service;


import com.example.demo.feed.domain.FeedRepository;
import com.example.demo.friend.domain.FriendShipRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TimeLineTest {


    @Mock
    FriendShipRepository friendShipRepository;

    @Mock
    FeedRepository feedRepository;

    @Mock
    UserService userService;


    @DisplayName("타임라인 테스트 1. 페이지 정보 또는 크기 잘못 입력한 경우")
    @Test
    void test1(){
        TimeLineService sut
                = new TimeLineService(
                        friendShipRepository, feedRepository,
                userService
        );

        assertThrows(IllegalArgumentException.class,
                ()->sut.findMyTimeLine(-1, -1));


    }

}
