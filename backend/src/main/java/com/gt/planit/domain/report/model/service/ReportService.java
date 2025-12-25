package com.gt.planit.domain.report.model.service;

import com.gt.planit.domain.report.model.entity.ContentTypeCount;
import com.gt.planit.domain.report.model.entity.RegionCount;
import com.gt.planit.domain.report.model.entity.TravelStyle;
import com.gt.planit.domain.report.model.entity.UserInsightReport;  // 추가
import com.gt.planit.domain.report.model.entity.UserStats;
import com.gt.planit.domain.report.model.mapper.ReportMapper;
import com.gt.planit.domain.report.model.mapper.UserInsightReportMapper;  // 추가
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  // 추가

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportMapper reportMapper;
    private final UserInsightReportMapper insightReportMapper;  // 추가
    private final TravelStyleCalculator travelStyleCalculator;  // 추가
    private final TripAnalysisAiService aiService;  // 추가

    /**
     * 사용자 통계 데이터 수집 (기존 메서드 - 수정 없음)
     */
    public UserStats collectStats(Long userId) {
        log.info("사용자 통계 데이터 수집 시작: userId={}", userId);

        // 기본 통계
        Integer totalSpots = reportMapper.getTotalSpotsByUser(userId);
        Integer totalLikes = reportMapper.getTotalLikesByUser(userId);
        Integer totalReviews = reportMapper.getTotalReviewsByUser(userId);
        Double avgRating = reportMapper.getAvgRatingByUser(userId);

        // 지역 통계
        List<RegionCount> regions = reportMapper.getTopRegionsByUser(userId);

        // 콘텐츠 타입 통계
        List<ContentTypeCount> contentTypes = reportMapper.getContentTypeDistributionByUser(userId);

        // 해시태그
        List<String> topHashtags = reportMapper.getTopHashtagsByUser(userId, 5);

        UserStats stats = UserStats.builder()
                .totalSpots(totalSpots)
                .totalLikes(totalLikes)
                .totalReviews(totalReviews)
                .avgRating(avgRating)
                .regions(regions)
                .contentTypes(contentTypes)
                .topHashtags(topHashtags)
                .build();

        log.info("사용자 통계 데이터 수집 완료: userId={}", userId);

        return stats;
    }

    // ========== 여기서부터 새로 추가 ==========

    /**
     * AI 분석 조회 또는 생성
     */
    @Transactional
    public UserInsightReport getOrCreateAiAnalysis(Long userId, UserStats stats, TravelStyle style) {
        log.info("AI 분석 조회: userId={}", userId);

        // 1. DB에서 기존 분석 조회
        UserInsightReport existingReport = insightReportMapper.getReportByUserId(userId);

        if (existingReport != null &&
                existingReport.getAiAnalysis() != null &&
                !existingReport.getAiAnalysis().isEmpty()) {

            log.info("기존 AI 분석 사용: userId={}", userId);
            return existingReport;
        }

        // 2. 없으면 새로 생성
        log.info("새 AI 분석 생성: userId={}", userId);
        return createAndSaveAiAnalysis(userId, stats, style);
    }

    /**
     * AI 분석 생성 및 저장
     */
    @Transactional
    public UserInsightReport createAndSaveAiAnalysis(Long userId, UserStats stats, TravelStyle style) {
        // AI 생성
        String aiAnalysis = aiService.generateTravelStyleAnalysis(stats, style);
        String aiRecommendation = aiService.generateRecommendation(stats, style);

        // DB 저장
        UserInsightReport report = UserInsightReport.builder()
                .userId(userId)
                .travelStyle(style.getType().name())
                .aiAnalysis(aiAnalysis)
                .aiRecommendation(aiRecommendation)
                .build();

        insightReportMapper.upsertReport(report);

        log.info("AI 분석 저장 완료: userId={}", userId);
        return report;
    }

    /**
     * AI 분석 재생성
     */
    @Transactional
    public UserInsightReport regenerateAiAnalysis(Long userId, UserStats stats, TravelStyle style) {
        log.info("AI 분석 재생성: userId={}", userId);

        // 새로 생성 (기존 것 덮어쓰기)
        return createAndSaveAiAnalysis(userId, stats, style);
    }
}