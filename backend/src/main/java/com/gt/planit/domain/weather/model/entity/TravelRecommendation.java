package com.gt.planit.domain.weather.model.entity;

import lombok.Getter;

@Getter
public enum TravelRecommendation {
    GOOD("여행하기 좋음", "#4CAF50"),
    MODERATE("보통", "#FFC107"),
    CAUTION("주의", "#FF9800"),
    NOT_RECOMMENDED("비추천", "#F44336");

    private final String description;
    private final String colorCode;

    TravelRecommendation(String description, String colorCode) {
        this.description = description;
        this.colorCode = colorCode;
    }
}