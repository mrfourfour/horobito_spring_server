package com.example.demo.preperredPerson.controller;

import com.example.demo.preperredPerson.service.PreferredPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PreferredPersionController {

    private final PreferredPersonService preferredPersonService;

    @PostMapping("/feeds/{feedId}/likes")
    public void likeFeedByFeedId(@PathVariable Long feedId){
        preferredPersonService.likeFeedByFeedID(feedId);
    }
}
