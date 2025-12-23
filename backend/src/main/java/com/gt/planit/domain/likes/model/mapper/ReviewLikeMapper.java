package com.gt.planit.domain.likes.model.mapper;

import com.gt.planit.domain.likes.model.dto.ReviewLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewLikeMapper {

    ReviewLike findByUserAndReview(@Param("userId") Long userId,
                                   @Param("reviewId") Long reviewId);

    void insert(@Param("userId") Long userId,
                @Param("reviewId") Long reviewId);

    void updateIsDeleted(@Param("id") Long id,
                         @Param("isDeleted") String isDeleted);

    int countByReview(@Param("reviewId") Long reviewId);
}
