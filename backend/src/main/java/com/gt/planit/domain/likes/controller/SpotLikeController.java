package com.gt.planit.domain.likes.controller;

import com.gt.planit.domain.likes.model.dto.MySpotLikeResDto;
import com.gt.planit.domain.likes.model.service.SpotLikeService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/v1/my-page/likes")
    public ResponseEntity<List<MySpotLikeResDto>> getMyLikes(
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        return ResponseEntity.ok(spotLikeService.getLikedSpots(userId));
    }

}
