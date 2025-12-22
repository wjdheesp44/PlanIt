package com.gt.planit.domain.review.controller;

import com.gt.planit.domain.review.model.dto.MyReviewResDto;
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
@RequestMapping("/v1/my-page/reviews")
public class MyReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<MyReviewResDto> getReviewsByUserId(@AuthenticationPrincipal CustomUserDetails user) {
        return reviewService.getReviewsByEmail(user.getUsername());
    }

    @PutMapping("/{reviewId}")
    public void update(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDto req
    ) {
        reviewService.updateReview(user.getId(), reviewId, req);
    }

    @DeleteMapping("/{reviewId}")
    public void delete(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(user.getId(), reviewId);
    }
}
