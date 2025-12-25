package com.gt.planit.domain.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStats {
    // 기본 통계
    private Integer totalSpots;
    private Integer totalLikes;
    private Integer totalReviews;
    private Double avgRating;

    // 지역 통계
    private List<RegionCount> regions;

    // 콘텐츠 타입 통계
    private List<ContentTypeCount> contentTypes;  // 이제 별도 클래스로 참조

    // 해시태그
    private List<String> topHashtags;

    // 편의 메서드
    public RegionCount getTopRegion() {
        return regions != null && !regions.isEmpty() ? regions.get(0) : null;
    }

    public ContentTypeCount getTopContentType() {
        return contentTypes != null && !contentTypes.isEmpty() ? contentTypes.get(0) : null;
    }
}