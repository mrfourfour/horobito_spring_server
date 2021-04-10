package com.example.demo.preperredPerson.controller;

import com.example.demo.preperredPerson.service.PreferredPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@RestController
@RequiredArgsConstructor
public class PreferredPersonController {

    private final PreferredPersonService preferredPersonService;

    @PostMapping("/feeds/{feedId}/likes")
    public void likeFeedByFeedId(@PathVariable Long feedId) throws AccessDeniedException {
        preferredPersonService.likeFeedByFeedID(feedId);
    }
}
