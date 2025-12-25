package com.gt.planit.domain.review.model.service;

import com.gt.planit.domain.review.model.dto.MyReviewResDto;
import com.gt.planit.domain.review.model.dto.ReviewDto;
import com.gt.planit.domain.review.model.dto.ReviewReqDto;
import com.gt.planit.domain.review.model.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewImageStore reviewImageStore;

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviews(Long spotId) {
        List<ReviewDto> reviews = reviewMapper.selectReviewsBySpotId(spotId);

        for (ReviewDto review : reviews) {
            List<String> images =
                    reviewMapper.selectReviewImages(review.getId());
            review.setImages(images);
        }

        return reviews;
    }

    @Transactional(readOnly = true)
    public List<MyReviewResDto> getReviewsByEmail(String username) {
        return reviewMapper.selectReviewsByEmail(username);
    }

    @Transactional
    public void createReview(Long userId, Long spotId, ReviewReqDto req, List<MultipartFile> images) {
        // 1. 유효성 검사
        validate(req.getRating(), req.getContent());

        // 2. 리뷰 저장
        reviewMapper.insertReview(
                userId,
                spotId,
                req.getRating(),
                req.getContent()
        );

        // 3. 방금 저장된 review_id 조회
        Long reviewId = reviewMapper.findLastInsertId();
        // 또는 getGeneratedKey 방식이면 그걸 사용

        // 4. 이미지 저장 (있을 때만)
        if (images != null && !images.isEmpty()) {

            if (images.size() > 10) {
                throw new IllegalArgumentException("이미지는 최대 10장까지 가능합니다.");
            }

            int order = 0;
            for (MultipartFile image : images) {
                if (image.isEmpty()) continue;

                // (1) 파일 저장 → URL 문자열 반환
                String imageUrl = reviewImageStore.save(image);

                // (2) DB 저장
                reviewMapper.insertReviewImage(
                        reviewId,
                        imageUrl,
                        order++
                );
            }
        }

        // 5. 스팟 평점 반영
        reviewMapper.increaseRating(spotId, req.getRating());
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

        if (review.getRating() != req.getRating()) {
            reviewMapper.updateRating(
                    review.getSpotId(),
                    review.getRating(),
                    req.getRating()
            );
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

        reviewMapper.decreaseRating(review.getSpotId(), review.getRating());
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
