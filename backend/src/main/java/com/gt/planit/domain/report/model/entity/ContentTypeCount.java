package com.gt.planit.domain.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentTypeCount {
    private String contentType;
    private Integer count;
    private Double percentage;
}