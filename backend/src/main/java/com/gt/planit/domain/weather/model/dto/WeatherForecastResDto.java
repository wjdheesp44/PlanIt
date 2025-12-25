package com.gt.planit.domain.weather.model.dto;

import com.gt.planit.domain.weather.model.entity.WeatherForecast;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherForecastResDto {
    private LocalDate forecastDate;
    private String forecastTime;
    private String weatherCondition;
    private String weatherEmoji;
    private BigDecimal temperature;
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;
    private Integer precipitationProbability;
    private String travelRecommendation;
    private String recommendationColor;

    public static WeatherForecastResDto from(WeatherForecast forecast) {
        return WeatherForecastResDto.builder()
                .forecastDate(forecast.getForecastDate())
                .forecastTime(forecast.getForecastTime())
                .weatherCondition(forecast.getWeatherCondition().getDescription())
                .weatherEmoji(forecast.getWeatherCondition().getEmoji())
                .temperature(forecast.getTemperature())
                .minTemperature(forecast.getMinTemperature())
                .maxTemperature(forecast.getMaxTemperature())
                .precipitationProbability(forecast.getPrecipitationProbability())
                .travelRecommendation(forecast.getTravelRecommendation().getDescription())
                .recommendationColor(forecast.getTravelRecommendation().getColorCode())
                .build();
    }
}
