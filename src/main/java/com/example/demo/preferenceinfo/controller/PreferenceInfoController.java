package com.example.demo.preferenceinfo.controller;

import com.example.demo.preferenceinfo.service.PreferenceResult;
import com.example.demo.preferenceinfo.service.PreferenceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@RestController
@RequiredArgsConstructor
public class PreferenceInfoController {

    private final PreferenceInfoService preferenceInfoService;

    @PostMapping("/feeds/{feedId}/likes")
    public void likeFeedByFeedId(@PathVariable Long feedId) throws AccessDeniedException {
        PreferenceResult result = preferenceInfoService.likeFeedByFeedId(feedId);

        switch (result){
            case SUCCESS:
                ResponseEntity.ok();
                break;
            case MY_FEED_ERROR:
            case FEED_NOT_FOUND:
                ResponseEntity.status(HttpStatus.BAD_REQUEST);
                break;
            case NOT_MY_FRIEND:
                ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }

    }
}
