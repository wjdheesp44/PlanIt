package com.gt.planit.domain.weather.model.service;

import com.gt.planit.domain.weather.model.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherSchedulerService {

    private final WeatherApiService weatherApiService;
    private final WeatherMapper weatherMapper;

//    @Scheduled(cron = "0 0 5 * * *")
    public void updateShortTermForecasts() {
        log.info("단기예보 갱신 시작");

        try {
            List<Map<String, Object>> guguns = weatherMapper.selectAllGugunsWithGrid();

            for (Map<String, Object> gugun : guguns) {
                Long gugunId = (Long) gugun.get("id");
                Integer gridX = (Integer) gugun.get("grid_x");
                Integer gridY = (Integer) gugun.get("grid_y");
                log.info("{} {} {}", gridX, gridY, gugunId);
                if (gridX != null && gridY != null) {
                    weatherApiService.fetchAndSaveShortTermForecast(gugunId, gridX, gridY);
                    Thread.sleep(100);
                }
            }

            log.info("단기예보 갱신 완료");

        } catch (Exception e) {
            log.error("단기예보 갱신 실패", e);
        }
    }

//    @Scheduled(cron = "0 0 6,18 * * *")
    public void updateMidTermForecasts() {
        log.info("중기예보 갱신 시작");

        try {
            List<Map<String, Object>> regions = weatherMapper.selectDistinctMidRegIds();

            for (Map<String, Object> region : regions) {
                String midLandRegId = (String) region.get("mid_land_reg_id");
                String midTempRegId = (String) region.get("mid_temp_reg_id");

                if (midLandRegId != null) {
                    weatherApiService.fetchAndSaveMidTermForecast(midLandRegId, midTempRegId);
                    Thread.sleep(100);
                }
            }

            log.info("중기예보 갱신 완료");

        } catch (Exception e) {
            log.error("중기예보 갱신 실패", e);
        }
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
