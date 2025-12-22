package com.gt.planit.domain.plan.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReorderPlanReqDto {
    private Long planId;
    private Integer newOrder;
}
