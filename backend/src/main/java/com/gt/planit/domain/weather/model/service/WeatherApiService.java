package com.gt.planit.domain.weather.model.service;

import com.gt.planit.domain.weather.api.MidTermWeatherApi;
import com.gt.planit.domain.weather.api.ShortTermWeatherApi;
import com.gt.planit.domain.weather.model.dto.MidTermApiResDto;
import com.gt.planit.domain.weather.model.dto.ShortTermApiResDto;
import com.gt.planit.domain.weather.model.entity.ForecastType;
import com.gt.planit.domain.weather.model.entity.TravelRecommendation;
import com.gt.planit.domain.weather.model.entity.WeatherCondition;
import com.gt.planit.domain.weather.model.entity.WeatherForecast;
import com.gt.planit.domain.weather.model.mapper.WeatherMapper;
import com.gt.planit.domain.weather.util.WeatherConditionCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherApiService {

    private final ShortTermWeatherApi shortTermApi;
    private final MidTermWeatherApi midTermApi;
    private final WeatherMapper weatherMapper;
    private final WeatherConditionCalculator weatherCalculator;

    /**
     * 단기예보 데이터 수집 및 저장
     */
    @Transactional
    public void fetchAndSaveShortTermForecast(Long gugunId, int gridX, int gridY) {
        try {
            log.info("단기예보 수집 시작 - gugunId: {}, gridX: {}, gridY: {}", gugunId, gridX, gridY);

            ShortTermApiResDto response = shortTermApi.getShortTermForecast(gridX, gridY);

            if (!"00".equals(response.getResponse().getHeader().getResultCode())) {
                log.error("단기예보 API 오류: {}", response.getResponse().getHeader().getResultMsg());
                return;
            }

            List<ShortTermApiResDto.Item> items = response.getResponse().getBody().getItems().getItem();

            Map<String, Map<String, String>> groupedData = items.stream()
                    .collect(Collectors.groupingBy(
                            item -> item.getFcstDate() + item.getFcstTime(),
                            Collectors.toMap(
                                    ShortTermApiResDto.Item::getCategory,
                                    ShortTermApiResDto.Item::getFcstValue,
                                    (v1, v2) -> v1
                            )
                    ));

            LocalDate baseDate = LocalDate.now();
            String baseTime = "0500";

            groupedData.forEach((dateTime, categoryMap) -> {
                WeatherForecast forecast = buildShortTermForecast(
                        gugunId, dateTime, categoryMap, baseDate, baseTime
                );
                weatherMapper.insertOrUpdateWeatherForecast(forecast);
            });

            log.info("단기예보 저장 완료 - gugunId: {}, 저장 건수: {}", gugunId, groupedData.size());

        } catch (Exception e) {
            log.error("단기예보 수집 실패 - gugunId: {}", gugunId, e);
        }
    }

    /**
     * 중기예보 데이터 수집 및 저장
     */
    @Transactional
    public void fetchAndSaveMidTermForecast(String midLandRegId, String midTempRegId) {
        try {
            log.info("중기예보 수집 시작 - regId: {}", midLandRegId);

            MidTermApiResDto response = midTermApi.getMidTermForecast(midLandRegId);

            if (!"00".equals(response.getResponse().getHeader().getResultCode())) {
                log.error("중기예보 API 오류: {}", response.getResponse().getHeader().getResultMsg());
                return;
            }

            MidTermApiResDto.Item item = response.getResponse().getBody().getItems().getItem().get(0);

            LocalDate baseDate = LocalDate.now();
            String baseTime = "0600";

            List<Long> gugunIds = weatherMapper.selectGugunIdsByMidLandRegId(midLandRegId);

            for (int day = 3; day <= 10; day++) {
                LocalDate forecastDate = baseDate.plusDays(day);

                for (Long gugunId : gugunIds) {
                    WeatherForecast amForecast = buildMidTermForecast(
                            gugunId, forecastDate, "0600", day, true, item, baseDate, baseTime, midLandRegId
                    );
                    WeatherForecast pmForecast = buildMidTermForecast(
                            gugunId, forecastDate, "1800", day, false, item, baseDate, baseTime, midLandRegId
                    );

                    weatherMapper.insertOrUpdateWeatherForecast(amForecast);
                    weatherMapper.insertOrUpdateWeatherForecast(pmForecast);
                }
            }

            log.info("중기예보 저장 완료 - regId: {}, 구군 수: {}", midLandRegId, gugunIds.size());

        } catch (Exception e) {
            log.error("중기예보 수집 실패 - regId: {}", midLandRegId, e);
        }
    }

    private WeatherForecast buildShortTermForecast(
            Long gugunId, String dateTime, Map<String, String> data,
            LocalDate baseDate, String baseTime) {

        LocalDate forecastDate = LocalDate.parse(dateTime.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
        String forecastTime = dateTime.substring(8);

        String sky = data.get("SKY");
        String pty = data.get("PTY");
        WeatherCondition condition = weatherCalculator.mapShortTermWeather(sky, pty);

        TravelRecommendation recommendation = weatherCalculator.calculateRecommendation(
                condition,
                data.get("POP") != null ? Integer.parseInt(data.get("POP")) : null,
                data.get("PCP")
        );

        return WeatherForecast.builder()
                .gugunId(gugunId)
                .forecastDate(forecastDate)
                .forecastTime(forecastTime)
                .forecastType(ForecastType.SHORT_TERM)
                .weatherCondition(condition)
                .skyCode(sky)
                .precipitationType(pty)
                .temperature(data.get("TMP") != null ? new java.math.BigDecimal(data.get("TMP")) : null)
                .precipitationProbability(data.get("POP") != null ? Integer.parseInt(data.get("POP")) : null)
                .precipitationAmount(data.get("PCP"))
                .snowAmount(data.get("SNO"))
                .humidity(data.get("REH") != null ? Integer.parseInt(data.get("REH")) : null)
                .windSpeed(data.get("WSD") != null ? new java.math.BigDecimal(data.get("WSD")) : null)
                .travelRecommendation(recommendation)
                .baseDate(baseDate)
                .baseTime(baseTime)
                .build();
    }

    private WeatherForecast buildMidTermForecast(
            Long gugunId, LocalDate forecastDate, String forecastTime, int day,
            boolean isAm, MidTermApiResDto.Item item,
            LocalDate baseDate, String baseTime, String midLandRegId) {

        String weatherText = getWeatherText(item, day, isAm);
        Integer precipProb = getPrecipProb(item, day, isAm);

        WeatherCondition condition = weatherCalculator.mapMidTermWeather(weatherText);
        TravelRecommendation recommendation = weatherCalculator.calculateRecommendation(
                condition, precipProb, null
        );

        return WeatherForecast.builder()
                .gugunId(gugunId)
                .forecastDate(forecastDate)
                .forecastTime(forecastTime)
                .forecastType(ForecastType.MID_TERM)
                .weatherCondition(condition)
                .precipitationProbability(precipProb)
                .travelRecommendation(recommendation)
                .baseDate(baseDate)
                .baseTime(baseTime)
                .midLandRegId(midLandRegId)
                .build();
    }

    private String getWeatherText(MidTermApiResDto.Item item, int day, boolean isAm) {
        return switch (day) {
            case 3 -> isAm ? item.getWf3Am() : item.getWf3Pm();
            case 4 -> isAm ? item.getWf4Am() : item.getWf4Pm();
            case 5 -> isAm ? item.getWf5Am() : item.getWf5Pm();
            case 6 -> isAm ? item.getWf6Am() : item.getWf6Pm();
            case 7 -> isAm ? item.getWf7Am() : item.getWf7Pm();
            case 8 -> item.getWf8();
            case 9 -> item.getWf9();
            case 10 -> item.getWf10();
            default -> "흐림";
        };
    }

    private Integer getPrecipProb(MidTermApiResDto.Item item, int day, boolean isAm) {
        return switch (day) {
            case 3 -> isAm ? item.getRnSt3Am() : item.getRnSt3Pm();
            case 4 -> isAm ? item.getRnSt4Am() : item.getRnSt4Pm();
            case 5 -> isAm ? item.getRnSt5Am() : item.getRnSt5Pm();
            case 6 -> isAm ? item.getRnSt6Am() : item.getRnSt6Pm();
            case 7 -> isAm ? item.getRnSt7Am() : item.getRnSt7Pm();
            case 8 -> item.getRnSt8();
            case 9 -> item.getRnSt9();
            case 10 -> item.getRnSt10();
            default -> 0;
        };
    }
}
