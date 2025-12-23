// WeatherForecast.java
package com.gt.planit.domain.weather.model.entity;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherForecast {
    private Long id;
    private LocalDate forecastDate;
    private String forecastTime;
    private ForecastType forecastType;

    private WeatherCondition weatherCondition;
    private String skyCode;
    private String precipitationType;

    private BigDecimal temperature;
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;

    private Integer precipitationProbability;
    private String precipitationAmount;
    private String snowAmount;

    private Integer humidity;
    private BigDecimal windSpeed;

    private TravelRecommendation travelRecommendation;

    private LocalDate baseDate;
    private String baseTime;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long gugunId;
    private String midLandRegId;
}