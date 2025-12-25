package com.gt.planit.domain.review.controller;

import com.gt.planit.domain.review.model.dto.ReviewDto;
import com.gt.planit.domain.review.model.dto.ReviewReqDto;
import com.gt.planit.domain.review.model.service.ReviewService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/spots/{spotId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> list(@PathVariable Long spotId) {
        return reviewService.getReviews(spotId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long spotId,
            @RequestPart(value="req") ReviewReqDto req,
            @RequestPart(value = "images", required = false)
            List<MultipartFile> images
    ) {

        reviewService.createReview(user.getId(), spotId, req, images);
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
