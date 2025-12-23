package com.gt.planit.domain.weather.util;

import com.gt.planit.domain.weather.model.entity.*;
import org.springframework.stereotype.Component;

@Component
public class WeatherConditionCalculator {

    public WeatherCondition mapShortTermWeather(String sky, String pty) {
        if (pty != null && !"0".equals(pty)) {
            return switch(pty) {
                case "1", "4" -> WeatherCondition.RAIN;
                case "2" -> WeatherCondition.RAIN_SNOW;
                case "3" -> WeatherCondition.SNOW;
                default -> WeatherCondition.CLOUDY;
            };
        }

        if (sky != null) {
            return switch(sky) {
                case "1" -> WeatherCondition.CLEAR;
                case "3" -> WeatherCondition.PARTLY_CLOUDY;
                case "4" -> WeatherCondition.CLOUDY;
                default -> WeatherCondition.CLEAR;
            };
        }

        return WeatherCondition.CLEAR;
    }

    public WeatherCondition mapMidTermWeather(String weatherText) {
        if (weatherText == null) return WeatherCondition.CLOUDY;

        if (weatherText.contains("맑음")) return WeatherCondition.CLEAR;
        if (weatherText.contains("구름많음")) return WeatherCondition.PARTLY_CLOUDY;
        if (weatherText.contains("흐림")) return WeatherCondition.CLOUDY;
        if (weatherText.contains("비") && weatherText.contains("눈")) return WeatherCondition.RAIN_SNOW;
        if (weatherText.contains("비") || weatherText.contains("소나기")) return WeatherCondition.RAIN;
        if (weatherText.contains("눈")) return WeatherCondition.SNOW;

        return WeatherCondition.CLOUDY;
    }

    public TravelRecommendation calculateRecommendation(
            WeatherCondition condition,
            Integer precipProb,
            String precipAmount) {

        if (condition == WeatherCondition.SNOW || condition == WeatherCondition.RAIN_SNOW) {
            return TravelRecommendation.NOT_RECOMMENDED;
        }

        if (condition == WeatherCondition.RAIN) {
            if (precipProb != null && precipProb >= 70) {
                return TravelRecommendation.NOT_RECOMMENDED;
            }
            if (precipAmount != null && !precipAmount.equals("강수없음") && !precipAmount.equals("0mm")) {
                return TravelRecommendation.CAUTION;
            }
        }

        if (condition == WeatherCondition.CLOUDY) {
            return TravelRecommendation.MODERATE;
        }

        if (condition == WeatherCondition.PARTLY_CLOUDY) {
            return precipProb != null && precipProb >= 50
                    ? TravelRecommendation.MODERATE
                    : TravelRecommendation.GOOD;
        }

        return TravelRecommendation.GOOD;
    }
}