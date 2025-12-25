package com.gt.planit.domain.weather.model.service;

import com.gt.planit.domain.weather.model.dto.*;
import com.gt.planit.domain.weather.model.entity.*;
import com.gt.planit.domain.weather.model.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private final WeatherMapper weatherMapper;

    /**
     * 캘린더용 날씨 예보 조회 (일별 요약, 단기예보 우선)
     */
    public List<WeatherCalendarResDto> getWeatherCalendar(WeatherSearchReqDto request) {
        List<WeatherForecast> forecasts = weatherMapper.selectWeatherForecastsByDateRange(
                request.getGugunId(),
                request.getStartDate(),
                request.getEndDate()
        );

        // ⭐ 날짜별로 그룹화하면서 단기예보 우선
        Map<LocalDate, List<WeatherForecast>> forecastsByDate = forecasts.stream()
                .collect(Collectors.groupingBy(WeatherForecast::getForecastDate));

        return forecastsByDate.entrySet().stream()
                .map(entry -> {
                    List<WeatherForecast> dailyForecasts = entry.getValue();

                    // ⭐ 단기예보가 있으면 단기예보만 사용
                    boolean hasShortTerm = dailyForecasts.stream()
                            .anyMatch(f -> "SHORT_TERM".equals(f.getForecastType()));

                    if (hasShortTerm) {
                        dailyForecasts = dailyForecasts.stream()
                                .filter(f -> "SHORT_TERM".equals(f.getForecastType()))
                                .collect(Collectors.toList());
                    }

                    return buildCalendarResponse(entry.getKey(), dailyForecasts);
                })
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .collect(Collectors.toList());
    }

    /**
     * 특정 날짜의 시간별 상세 예보 (단기예보 우선)
     */
    public List<WeatherForecastResDto> getWeatherDetail(Long gugunId, LocalDate date) {
        List<WeatherForecast> forecasts = weatherMapper.selectWeatherForecastsByDate(gugunId, date);

        // ⭐ 단기예보가 있으면 단기예보만 반환
        boolean hasShortTerm = forecasts.stream()
                .anyMatch(f -> "SHORT_TERM".equals(f.getForecastType()));

        if (hasShortTerm) {
            forecasts = forecasts.stream()
                    .filter(f -> "SHORT_TERM".equals(f.getForecastType()))
                    .collect(Collectors.toList());
        }

        return forecasts.stream()
                .map(WeatherForecastResDto::from)
                .sorted((a, b) -> {
                    // 시간순 정렬
                    String timeA = a.getForecastTime() != null ? a.getForecastTime() : "0000";
                    String timeB = b.getForecastTime() != null ? b.getForecastTime() : "0000";
                    return timeA.compareTo(timeB);
                })
                .collect(Collectors.toList());
    }

    /**
     * 여행 추천 날짜 조회
     */
    public List<LocalDate> getRecommendedDates(WeatherSearchReqDto request) {
        List<WeatherForecast> forecasts = weatherMapper.selectWeatherForecastsByDateRange(
                request.getGugunId(),
                request.getStartDate(),
                request.getEndDate()
        );

        return forecasts.stream()
                .filter(f -> f.getTravelRecommendation() == TravelRecommendation.GOOD)
                .map(WeatherForecast::getForecastDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * 캘린더 응답 빌드
     */
    private WeatherCalendarResDto buildCalendarResponse(LocalDate date, List<WeatherForecast> forecasts) {
        WeatherForecast representative = selectRepresentativeForecast(forecasts);

        String minTemp = forecasts.stream()
                .map(f -> f.getMinTemperature() != null ? f.getMinTemperature() : f.getTemperature())
                .filter(t -> t != null)
                .min((a, b) -> a.compareTo(b))
                .map(t -> t + "°C")
                .orElse("-");

        String maxTemp = forecasts.stream()
                .map(f -> f.getMaxTemperature() != null ? f.getMaxTemperature() : f.getTemperature())
                .filter(t -> t != null)
                .max((a, b) -> a.compareTo(b))
                .map(t -> t + "°C")
                .orElse("-");

        List<WeatherForecastResDto> hourly = forecasts.stream()
                .filter(f -> f.getForecastTime() != null)
                .map(WeatherForecastResDto::from)
                .collect(Collectors.toList());

        return WeatherCalendarResDto.builder()
                .date(date)
                .weatherCondition(representative.getWeatherCondition().getDescription())
                .weatherEmoji(representative.getWeatherCondition().getEmoji())
                .minTemperature(minTemp)
                .maxTemperature(maxTemp)
                .travelRecommendation(representative.getTravelRecommendation().getDescription())
                .recommendationColor(representative.getTravelRecommendation().getColorCode())
                .hourlyForecasts(hourly.isEmpty() ? null : hourly)
                .build();
    }

    private WeatherForecast selectRepresentativeForecast(List<WeatherForecast> forecasts) {
        return forecasts.stream()
                .filter(f -> f.getForecastTime() != null &&
                        Integer.parseInt(f.getForecastTime()) >= 1200 &&
                        Integer.parseInt(f.getForecastTime()) <= 1800)
                .findFirst()
                .orElseGet(() -> forecasts.stream()
                        .min((a, b) -> a.getTravelRecommendation().compareTo(b.getTravelRecommendation()))
                        .orElse(forecasts.get(0)));
    }
}