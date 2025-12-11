package com.gt.planit.domain.spot.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Builder
public class SpotSearchCondition {
    private List<String> types;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> weathers;
    private List<Integer> ratings;
    private Integer likesCountMin;
    private String searchKeyword;
    private List<String> tags;
    private List<Long> regionIds;
    private String sort;
}
