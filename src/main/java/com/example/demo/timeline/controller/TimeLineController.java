package com.example.demo.timeline.controller;


import com.example.demo.timeline.service.TimeLineFindResult;
import com.example.demo.timeline.service.TimeLineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;

@Controller
@AllArgsConstructor
public class TimeLineController {

    private final TimeLineService timeLineService;

    @GetMapping("/feeds")
    public Object findMyTimeLine(@RequestParam(value="page") int page,
                                 @RequestParam(value = "size") int size) throws AccessDeniedException {
        Object result = timeLineService.findMyTimeLine(page, size);

        if (result instanceof TimeLineFindResult){
            switch ((TimeLineFindResult)result){
                case BAD_REQUEST:
                    ResponseEntity.status(HttpStatus.BAD_REQUEST);
            }
        }else {
            ResponseEntity.ok();
        }
        return result;
    }
}
