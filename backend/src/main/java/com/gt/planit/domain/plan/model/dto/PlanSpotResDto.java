package com.gt.planit.domain.plan.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanSpotResDto {
    private Long id;
    private Long spotId;
    private String name;
    private String badge;
    private String location;
    private String image;
    private String memo;
    private Boolean isFavorite;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer sortOrder;

}
