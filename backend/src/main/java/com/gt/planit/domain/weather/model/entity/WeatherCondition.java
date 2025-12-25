package com.gt.planit.domain.weather.model.entity;

import lombok.Getter;

@Getter
public enum WeatherCondition {
    CLEAR("맑음", "☀️"),
    PARTLY_CLOUDY("구름많음", "⛅"),
    CLOUDY("흐림", "☁️"),
    RAIN("비", "🌧️"),
    SNOW("눈", "❄️"),
    RAIN_SNOW("비/눈", "🌨️");

    private final String description;
    private final String emoji;

    WeatherCondition(String description, String emoji) {
        this.description = description;
        this.emoji = emoji;
    }
}
