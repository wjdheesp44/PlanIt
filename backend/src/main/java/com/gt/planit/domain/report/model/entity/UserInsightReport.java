package com.gt.planit.domain.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInsightReport {
    private Long reportId;
    private Long userId;
    private String travelStyle;
    private String summaryText;  // 기존 컬럼
    private String aiAnalysis;
    private String aiRecommendation;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}