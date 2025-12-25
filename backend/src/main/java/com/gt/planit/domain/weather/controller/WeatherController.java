// WeatherController.java
package com.gt.planit.domain.weather.controller;

import com.gt.planit.domain.weather.model.dto.*;
import com.gt.planit.domain.weather.model.service.WeatherSchedulerService;
import com.gt.planit.domain.weather.model.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherSchedulerService schedulerService;

    /**
     * 구군별 날씨 예보 조회 (캘린더용)
     */
    @GetMapping("/calendar/{gugunId}")
    public ResponseEntity<List<WeatherCalendarResDto>> getWeatherCalendar(
            @PathVariable Long gugunId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        log.info("날씨 캘린더 조회 - gugunId: {}, startDate: {}, endDate: {}",
                gugunId, startDate, endDate);

        WeatherSearchReqDto request = WeatherSearchReqDto.builder()
                .gugunId(gugunId)
                .startDate(startDate != null ? startDate : LocalDate.now())
                .endDate(endDate != null ? endDate : LocalDate.now().plusDays(10))
                .build();

        List<WeatherCalendarResDto> calendar = weatherService.getWeatherCalendar(request);
        return ResponseEntity.ok(calendar);
    }

    /**
     * 특정 날짜의 시간별 상세 예보 조회
     */
    @GetMapping("/detail/{gugunId}")
    public ResponseEntity<List<WeatherForecastResDto>> getWeatherDetail(
            @PathVariable Long gugunId,
            @RequestParam LocalDate date) {

        log.info("날씨 상세 조회 - gugunId: {}, date: {}", gugunId, date);

        List<WeatherForecastResDto> forecasts = weatherService.getWeatherDetail(gugunId, date);
        return ResponseEntity.ok(forecasts);
    }

    /**
     * 여행 추천 날짜 조회 (GOOD인 날짜만)
     */
    @GetMapping("/recommend/{gugunId}")
    public ResponseEntity<List<LocalDate>> getRecommendedDates(
            @PathVariable Long gugunId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        log.info("여행 추천 날짜 조회 - gugunId: {}", gugunId);

        WeatherSearchReqDto request = WeatherSearchReqDto.builder()
                .gugunId(gugunId)
                .startDate(startDate != null ? startDate : LocalDate.now())
                .endDate(endDate != null ? endDate : LocalDate.now().plusDays(10))
                .build();

        List<LocalDate> recommendedDates = weatherService.getRecommendedDates(request);
        return ResponseEntity.ok(recommendedDates);
    }

    @PostMapping("/update/short-term")
    public ResponseEntity<String> updateShortTerm() {
        schedulerService.updateShortTermForecastsByGugunId();
        return ResponseEntity.ok("단기예보 업데이트 시작");
    }

    @PostMapping("/update/mid-term")
    public ResponseEntity<String> updateMidTerm() {
        schedulerService.updateMidTermForecasts();
        return ResponseEntity.ok("중기예보 업데이트 시작");
    }
}