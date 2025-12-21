package com.gt.planit.domain.review.model.mapper;

import com.gt.planit.domain.review.model.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<ReviewDto> selectReviewsBySpotId(@Param("spotId") Long spotId);

    List<ReviewDto> selectReviewsByEmail(@Param("username") String username);

    ReviewDto selectReviewById(@Param("id") Long id);

    int insertReview(
            @Param("userId") Long userId,
            @Param("spotId") Long spotId,
            @Param("rating") Integer rating,
            @Param("content") String content
    );

    int updateReview(
            @Param("id") Long id,
            @Param("rating") Integer rating,
            @Param("content") String content
    );

    int softDeleteReview(@Param("id") Long id);
}
