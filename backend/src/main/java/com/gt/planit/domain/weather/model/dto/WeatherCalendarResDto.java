package com.gt.planit.domain.weather.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherCalendarResDto {
    private LocalDate date;
    private String weatherCondition;
    private String weatherEmoji;
    private String minTemperature;
    private String maxTemperature;
    private String travelRecommendation;
    private String recommendationColor;
    private List<WeatherForecastResDto> hourlyForecasts;
}
