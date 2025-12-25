package com.gt.planit.domain.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResDto {
    private TravelStyleResDto travelStyle;
    private StatisticsResDto statistics;
    private List<RegionStatsResDto> regions;
    private List<ContentTypeStatsResDto> contentTypes;
    private String aiAnalysis;
    private String aiRecommendation;
}