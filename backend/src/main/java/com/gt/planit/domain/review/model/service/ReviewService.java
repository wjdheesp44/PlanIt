package com.gt.planit.domain.review.model.service;

import com.gt.planit.domain.review.model.dto.ReviewDto;
import com.gt.planit.domain.review.model.dto.ReviewReqDto;
import com.gt.planit.domain.review.model.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviews(Long spotId) {
        return reviewMapper.selectReviewsBySpotId(spotId);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByEmail(String username) {
        return reviewMapper.selectReviewsByEmail(username);
    }

    @Transactional
    public void createReview(Long userId, Long spotId, ReviewReqDto req) {
        validate(req.getRating(), req.getContent());

        log.info(userId.toString());
        log.info(spotId.toString());
        log.info(req.getRating().toString());
        log.info(req.getContent());

        reviewMapper.insertReview(
                userId,
                spotId,
                req.getRating(),
                req.getContent()
        );
    }

    @Transactional
    public void updateReview(Long userId, Long reviewId, ReviewReqDto req) {
        validate(req.getRating(), req.getContent());

        ReviewDto review = reviewMapper.selectReviewById(reviewId);
        if (review == null) {
            throw new RuntimeException("후기가 존재하지 않습니다.");
        }

        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        reviewMapper.updateReview(reviewId, req.getRating(), req.getContent());
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        ReviewDto review = reviewMapper.selectReviewById(reviewId);
        if (review == null) {
            throw new RuntimeException("후기가 존재하지 않습니다.");
        }

        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        reviewMapper.softDeleteReview(reviewId);
    }

    private void validate(Integer rating, String content) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new RuntimeException("별점은 1~5점만 가능합니다.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("내용을 입력하세요.");
        }
        if (content.length() > 1000) {
            throw new RuntimeException("내용은 1000자 이내여야 합니다.");
        }
    }
}
