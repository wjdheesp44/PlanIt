package com.gt.planit.domain.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionCount {
    private String sidoName;
    private Integer count;
    private Double percentage;
}