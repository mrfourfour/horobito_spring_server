package com.example.demo.timeline.controller;


import com.example.demo.feed.service.FeedDto;
import com.example.demo.timeline.service.TimeLineFindResult;
import com.example.demo.timeline.service.TimeLineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@AllArgsConstructor
public class TimeLineController {

    private final TimeLineService timeLineService;

    @GetMapping("/feeds")
    public List<FeedDto> findMyTimeLine(@RequestParam(value="page") int page,
                                        @RequestParam(value = "size") int size) throws AccessDeniedException {


        try {
            List<FeedDto> result = timeLineService.findMyTimeLine(page, size);
            ResponseEntity.ok();
            return result;
        }catch (IllegalArgumentException ae){
            List<FeedDto> failedList = null;
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return failedList;
        }


    }
}
