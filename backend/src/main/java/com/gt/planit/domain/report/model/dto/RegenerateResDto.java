package com.gt.planit.domain.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegenerateResDto {
    private String aiAnalysis;
    private String aiRecommendation;
}