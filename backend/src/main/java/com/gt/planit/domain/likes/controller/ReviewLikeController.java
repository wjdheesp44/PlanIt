package com.gt.planit.domain.likes.controller;

import com.gt.planit.domain.likes.model.service.ReviewLikeService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/reviews")
public class ReviewLikeController {

    private final ReviewLikeService service;

    @PostMapping("/{reviewId}/likes")
    public ResponseEntity<?> like(@PathVariable Long reviewId,
                                  @AuthenticationPrincipal CustomUserDetails user) {

        int count = service.toggleLike(user.getId(), reviewId);

        return ResponseEntity.ok(Map.of(
                "helpfulCount", count
        ));
    }
}
