package com.gt.planit.domain.report.model.dto;

import com.gt.planit.domain.report.model.entity.RegionCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionStatsResDto {
    private String sidoName;
    private Integer count;
    private Double percentage;

    public static RegionStatsResDto from(RegionCount regionCount) {
        return RegionStatsResDto.builder()
                .sidoName(regionCount.getSidoName())
                .count(regionCount.getCount())
                .percentage(regionCount.getPercentage())
                .build();
    }

    public static List<RegionStatsResDto> fromList(List<RegionCount> regions) {
        return regions.stream()
                .map(RegionStatsResDto::from)
                .collect(Collectors.toList());
    }
}