package com.gt.planit.domain.likes.model.service;

import com.gt.planit.domain.likes.model.dto.ReviewLike;
import com.gt.planit.domain.likes.model.mapper.ReviewLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeMapper mapper;

    @Transactional
    public int toggleLike(Long userId, Long reviewId) {

        ReviewLike like = mapper.findByUserAndReview(userId, reviewId);

        if (like == null) {
            mapper.insert(userId, reviewId);
        } else {
            String next = like.getIsDeleted().equals("F") ? "T" : "F";
            mapper.updateIsDeleted(like.getId(), next);
        }

        return mapper.countByReview(reviewId);
    }
}
