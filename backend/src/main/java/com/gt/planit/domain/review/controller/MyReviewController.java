package com.gt.planit.domain.review.controller;

import com.gt.planit.domain.review.model.dto.ReviewDto;
import com.gt.planit.domain.review.model.dto.ReviewReqDto;
import com.gt.planit.domain.review.model.service.ReviewService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/my-page/reviews/{username}")
public class MyReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> getReviewsByUserId(@PathVariable String username) {
        return reviewService.getReviewsByEmail(username);
    }

    @PutMapping("/{reviewId}")
    public void update(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long spotId,
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDto req
    ) {
        reviewService.updateReview(user.getId(), reviewId, req);
    }

    @DeleteMapping("/{reviewId}")
    public void delete(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long spotId,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(user.getId(), reviewId);
    }
}
