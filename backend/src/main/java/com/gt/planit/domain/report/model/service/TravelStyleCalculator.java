package com.gt.planit.domain.report.model.service;

import com.gt.planit.domain.report.model.entity.ContentTypeCount;
import com.gt.planit.domain.report.model.entity.TravelStyle;
import com.gt.planit.domain.report.model.entity.TravelStyleType;
import com.gt.planit.domain.report.model.entity.UserStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TravelStyleCalculator {

    /**
     * 사용자 통계를 기반으로 여행 스타일 계산
     */
    public TravelStyle calculate(UserStats stats) {
        // 데이터가 부족한 경우
        if (stats.getTotalSpots() == null || stats.getTotalSpots() < 3) {
            return createBeginnerStyle();
        }

        Map<TravelStyleType, Integer> scores = new HashMap<>();

        // 1. 콘텐츠 타입 기반 점수 (가중치 60%)
        scores.put(TravelStyleType.CULTURAL_EXPLORER, calculateAttractionScore(stats) * 60 / 100);
        scores.put(TravelStyleType.FESTIVAL_HUNTER, calculateFestivalScore(stats) * 60 / 100);
        scores.put(TravelStyleType.TREND_SETTER, calculatePopupScore(stats) * 60 / 100);

        // 2. 지역 다양성 점수 (가중치 20%)
        int regionDiversity = calculateRegionDiversity(stats);
        scores.put(TravelStyleType.EXPLORER, regionDiversity * 20 / 100);

        // 3. 활동 집중도 점수 (가중치 20%)
        int localFocus = calculateLocalFocus(stats);
        scores.put(TravelStyleType.LOCAL_ENTHUSIAST, localFocus * 20 / 100);

        // 4. 균형도 점수
        int balance = calculateBalance(stats);
        scores.put(TravelStyleType.ALL_ROUNDER, balance);

        // 최고 점수 유형 결정
        TravelStyleType topStyle = Collections.max(scores.entrySet(),
                Map.Entry.comparingByValue()).getKey();

        log.info("여행 스타일 계산 완료: type={}, scores={}", topStyle, scores);

        return TravelStyle.builder()
                .type(topStyle)
                .scores(scores)
                .build();
    }

    private TravelStyle createBeginnerStyle() {
        return TravelStyle.builder()
                .type(TravelStyleType.BEGINNER)
                .scores(new HashMap<>())
                .build();
    }

    private int calculateAttractionScore(UserStats stats) {
        int score = 0;

        // ATTRACTION 비율
        int attractionPercentage = stats.getContentTypes().stream()
                .filter(ct -> "ATTRACTION".equals(ct.getContentType()))
                .findFirst()
                .map(ContentTypeCount::getPercentage)
                .map(Double::intValue)
                .orElse(0);
        score += attractionPercentage;

        // 리뷰 작성 보너스
        if (stats.getTotalReviews() != null && stats.getTotalReviews() >= 10) {
            score += 20;
        }
        if (stats.getAvgRating() != null && stats.getAvgRating() >= 4.0) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    private int calculateFestivalScore(UserStats stats) {
        int score = 0;

        // FESTIVAL 비율
        int festivalPercentage = stats.getContentTypes().stream()
                .filter(ct -> "FESTIVAL".equals(ct.getContentType()))
                .findFirst()
                .map(ContentTypeCount::getPercentage)
                .map(Double::intValue)
                .orElse(0);
        score += festivalPercentage;

        // 지역 다양성 보너스
        if (stats.getRegions() != null && stats.getRegions().size() >= 3) {
            score += 15;
        }

        return Math.min(score, 100);
    }

    private int calculatePopupScore(UserStats stats) {
        int score = 0;

        // POPUP 비율
        int popupPercentage = stats.getContentTypes().stream()
                .filter(ct -> "POPUP".equals(ct.getContentType()))
                .findFirst()
                .map(ContentTypeCount::getPercentage)
                .map(Double::intValue)
                .orElse(0);
        score += popupPercentage;

        // 해시태그 사용 보너스
        if (stats.getTopHashtags() != null && stats.getTopHashtags().size() >= 5) {
            score += 20;
        }

        return Math.min(score, 100);
    }

    private int calculateRegionDiversity(UserStats stats) {
        if (stats.getRegions() == null) return 0;

        int regionCount = stats.getRegions().size();

        if (regionCount >= 7) return 100;
        if (regionCount >= 5) return 70;
        if (regionCount >= 3) return 40;
        return 20;
    }

    private int calculateLocalFocus(UserStats stats) {
        if (stats.getRegions() == null || stats.getRegions().isEmpty()) return 0;

        // 가장 많이 방문한 지역 비율
        double topRegionRatio = stats.getRegions().get(0).getPercentage();

        if (topRegionRatio >= 70) return 100;
        if (topRegionRatio >= 50) return 60;
        return 30;
    }

    private int calculateBalance(UserStats stats) {
        if (stats.getContentTypes() == null || stats.getContentTypes().isEmpty()) return 0;

        List<Double> percentages = stats.getContentTypes().stream()
                .map(ContentTypeCount::getPercentage)
                .toList();

        // 표준편차 계산 (낮을수록 균형잡힘)
        double avg = percentages.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0);

        double variance = percentages.stream()
                .mapToDouble(p -> Math.pow(p - avg, 2))
                .average()
                .orElse(0);

        double stdDev = Math.sqrt(variance);

        // 표준편차가 낮을수록 높은 점수
        return (int) Math.max(0, 100 - (stdDev * 2));
    }
}