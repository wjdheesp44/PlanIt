package com.gt.planit.domain.plan.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanReqDto {
    private String memo;
    private Integer sortOrder;
}
