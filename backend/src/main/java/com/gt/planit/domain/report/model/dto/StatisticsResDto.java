package com.gt.planit.domain.report.model.dto;

import com.gt.planit.domain.report.model.entity.UserStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResDto {
    private Integer totalSpots;
    private Integer totalLikes;
    private Integer totalReviews;
    private Double avgRating;
    private List<String> topHashtags;

    public static StatisticsResDto from(UserStats userStats) {
        return StatisticsResDto.builder()
                .totalSpots(userStats.getTotalSpots())
                .totalLikes(userStats.getTotalLikes())
                .totalReviews(userStats.getTotalReviews())
                .avgRating(userStats.getAvgRating())
                .topHashtags(userStats.getTopHashtags())
                .build();
    }
}