package com.gt.planit.domain.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelStyle {
    private TravelStyleType type;
    private Map<TravelStyleType, Integer> scores;

    public String getName() {
        return type.getName();
    }

    public String getIcon() {
        return type.getIcon();
    }

    public String getDescription() {
        return type.getDescription();
    }
}