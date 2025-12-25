package com.gt.planit.domain.report.model.dto;

import com.gt.planit.domain.report.model.entity.TravelStyle;
import com.gt.planit.domain.report.model.entity.TravelStyleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelStyleResDto {
    private String type;
    private String name;
    private String icon;
    private String description;
    private Map<String, Integer> scores;

    public static TravelStyleResDto from(TravelStyle travelStyle) {
        return TravelStyleResDto.builder()
                .type(travelStyle.getType().name())
                .name(travelStyle.getName())
                .icon(travelStyle.getIcon())
                .description(travelStyle.getDescription())
                .scores(travelStyle.getScores().entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey().name(),
                                Map.Entry::getValue
                        )))
                .build();
    }
}