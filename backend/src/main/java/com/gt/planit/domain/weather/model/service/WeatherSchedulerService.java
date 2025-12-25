package com.gt.planit.domain.weather.model.service;

import com.gt.planit.domain.weather.model.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherSchedulerService {

    private final WeatherApiService weatherApiService;
    private final WeatherMapper weatherMapper;

    /**
     * 단기예보 갱신 (매일 오전 5시 30분)
     * API 발표 후 충분한 시간 확보
     * 제공 범위: 오늘 ~ +4일 (총 5일)
     */
//    @Scheduled(cron = "0 0 5 * * *")
    public void updateShortTermForecasts() {
        log.info("단기예보 갱신 시작");

        List<Map<String, Object>> guguns = weatherMapper.selectAllGugunsWithGrid();
        int successCount = 0;
        int failCount = 0;
        for (Map<String, Object> gugun : guguns) {
            Long gugunId = (Long) gugun.get("id");
            Integer gridX = (Integer) gugun.get("grid_x");
            Integer gridY = (Integer) gugun.get("grid_y");

            try {
                if (gridX != null && gridY != null) {
                    weatherApiService.fetchAndSaveShortTermForecast(gugunId, gridX, gridY);
                    successCount++;

                    // API 호출 제한 방지 (0.5초 대기)
                    Thread.sleep(500);
                }

            } catch (Exception e) {
                log.error("단기예보 저장 실패 - 구군ID: {}", gugunId, e);
                failCount++;
            }
        }

        log.info("========== 단기예보 갱신 완료 - 성공: {}, 실패: {} ==========", successCount, failCount);
    }

    /**
     * 중기예보 갱신 (매일 오전 6시 30분, 오후 6시 30분)
     * API 발표 후 충분한 시간 확보
     * 06시 발표: +4일 ~ +10일 (7일간)
     * 18시 발표: +5일 ~ +10일 (6일간)
     */
//    @Scheduled(cron = "0 0 6,18 * * *")
    @Transactional
    public void updateMidTermForecasts() {
        log.info("========== 중기예보 갱신 시작 ==========");

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();

        // ⭐ 발표 시간에 따라 시작일 결정
        int startDay;
        int endDay = 10;

        if (hour >= 6 && hour < 18) {
            // 오전 6시 발표: +4일부터
            startDay = 4;
            log.info("중기예보 (06시 발표): D+{} ~ D+{} (총 {}일)", startDay, endDay, (endDay - startDay + 1));
        } else {
            // 오후 6시 발표: +5일부터
            startDay = 5;
            log.info("중기예보 (18시 발표): D+{} ~ D+{} (총 {}일)", startDay, endDay, (endDay - startDay + 1));
        }

        // 중복되지 않는 지역 코드 조회
        List<Map<String, Object>> regions = weatherMapper.selectDistinctMidRegIds();
        log.info("중기예보 대상 지역 수: {}", regions.size());

        int successCount = 0;
        int failCount = 0;

        for (Map<String, Object> region : regions) {
            String midLandRegId = (String) region.get("mid_land_reg_id");

            if (midLandRegId == null || midLandRegId.isEmpty()) {
                continue;
            }

            try {
                // ⭐ startDay, endDay 전달
                weatherApiService.fetchAndSaveMidTermForecast(midLandRegId, startDay, endDay);
                successCount++;

                // API 호출 제한 방지 (1초 대기)
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("중기예보 저장 실패 - 지역코드: {}", midLandRegId, e);
                failCount++;
            }
        }

        log.info("========== 중기예보 갱신 완료 - 성공: {}, 실패: {} ==========",
                successCount, failCount);
    }

    public void updateShortTermForecastsByGugunId() {
        List<Long> gugunIds = Arrays.asList(
                58L
        );

        log.info("단기예보 갱신 시작");

        List<Map<String, Object>> guguns = weatherMapper.selectAllGugunsWithGrid();
        int successCount = 0;
        int failCount = 0;
        for (Map<String, Object> gugun : guguns) {
            Long gugunId = (Long) gugun.get("id");
            Integer gridX = (Integer) gugun.get("grid_x");
            Integer gridY = (Integer) gugun.get("grid_y");
            if (!gugunIds.contains(gugunId)) {
                continue;
            }
            try {
                if (gridX != null && gridY != null) {
                    weatherApiService.fetchAndSaveShortTermForecast(gugunId, gridX, gridY);
                    successCount++;

                    // API 호출 제한 방지 (0.5초 대기)
                    Thread.sleep(5000);
                }

            } catch (Exception e) {
                log.error("단기예보 저장 실패 - 구군ID: {}", gugunId, e);
                failCount++;
            }
        }

        log.info("========== 단기예보 갱신 완료 - 성공: {}, 실패: {} ==========", successCount, failCount);
    }

    public void updateShortTermForecastsTest() {
        log.info("단기예보 테스트 시작 (1개만)");

        try {
            List<Map<String, Object>> guguns = weatherMapper.selectAllGugunsWithGrid();

            if (!guguns.isEmpty()) {
                Map<String, Object> gugun = guguns.get(0); // 첫 번째 구군만
                Long gugunId = (Long) gugun.get("id");
                Integer gridX = (Integer) gugun.get("grid_x");
                Integer gridY = (Integer) gugun.get("grid_y");

                log.info("테스트 대상: gugunId={}, gridX={}, gridY={}", gugunId, gridX, gridY);

                if (gridX != null && gridY != null) {
                    weatherApiService.fetchAndSaveShortTermForecast(gugunId, gridX, gridY);
                    log.info("단기예보 테스트 완료");
                } else {
                    log.warn("격자 좌표가 없습니다");
                }
            } else {
                log.warn("구군 데이터가 없습니다");
            }

        } catch (Exception e) {
            log.error("단기예보 테스트 실패", e);
        }
    }
}
