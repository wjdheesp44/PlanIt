package com.gt.planit.domain.likes.controller;

import com.gt.planit.domain.likes.model.service.SpotLikeService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class SpotLikeController {

    private final SpotLikeService spotLikeService;

    @PostMapping("/v1/spots/{spotId}/likes")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long spotId,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean liked = spotLikeService.toggleSpotLike(user.getId(), spotId);
        return ResponseEntity.ok(liked);
    }

}
