package com.gt.planit.domain.report.model.mapper;

import com.gt.planit.domain.report.model.entity.ContentTypeCount;
import com.gt.planit.domain.report.model.entity.RegionCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {

    /**
     * 사용자가 저장한 총 스팟 개수
     */
    Integer getTotalSpotsByUser(@Param("userId") Long userId);

    /**
     * 사용자가 좋아요한 총 스팟 개수
     */
    Integer getTotalLikesByUser(@Param("userId") Long userId);

    /**
     * 사용자가 작성한 총 리뷰 개수
     */
    Integer getTotalReviewsByUser(@Param("userId") Long userId);

    /**
     * 사용자 리뷰 평균 평점
     */
    Double getAvgRatingByUser(@Param("userId") Long userId);

    /**
     * 사용자가 방문한 지역별 통계 (TOP 5)
     */
    List<RegionCount> getTopRegionsByUser(@Param("userId") Long userId);

    /**
     * 사용자가 선호하는 콘텐츠 타입별 통계
     */
    List<ContentTypeCount> getContentTypeDistributionByUser(@Param("userId") Long userId);

    /**
     * 사용자가 많이 사용한 해시태그 (TOP 5)
     */
    List<String> getTopHashtagsByUser(@Param("userId") Long userId, @Param("limit") int limit);
}