package com.gt.planit.domain.report.controller;

import com.gt.planit.domain.report.model.dto.*;
import com.gt.planit.domain.report.model.entity.TravelStyle;
import com.gt.planit.domain.report.model.entity.UserInsightReport;
import com.gt.planit.domain.report.model.entity.UserStats;
import com.gt.planit.domain.report.model.service.ReportService;
import com.gt.planit.domain.report.model.service.TravelStyleCalculator;
import com.gt.planit.domain.report.model.service.TripAnalysisAiService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final TravelStyleCalculator travelStyleCalculator;
//    private final TripAnalysisAiService aiService;

    /**
     * 사용자 분석 리포트 조회
     */
    @GetMapping
    public ResponseEntity<ReportResDto> getReport(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        log.info("리포트 조회 요청: userId={}", userId);

        try {
            // 1. 통계 데이터 수집
            UserStats stats = reportService.collectStats(userId);

            // 2. 여행 스타일 계산
            TravelStyle style = travelStyleCalculator.calculate(stats);

            // 3. AI 분석 조회 또는 생성 (수정된 부분)
            UserInsightReport aiReport = reportService.getOrCreateAiAnalysis(userId, stats, style);

            // 4. 응답 구성
            ReportResDto response = ReportResDto.builder()
                    .travelStyle(TravelStyleResDto.from(style))
                    .statistics(StatisticsResDto.from(stats))
                    .regions(RegionStatsResDto.fromList(stats.getRegions()))
                    .contentTypes(ContentTypeStatsResDto.fromList(stats.getContentTypes()))
                    .aiAnalysis(aiReport.getAiAnalysis())  // 수정
                    .aiRecommendation(aiReport.getAiRecommendation())  // 수정
                    .build();

            log.info("리포트 생성 완료: userId={}", userId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("리포트 생성 실패: userId={}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * AI 분석 재생성
     */
    @PostMapping("/regenerate")
    public ResponseEntity<RegenerateResDto> regenerateAiAnalysis(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        log.info("AI 분석 재생성 요청: userId={}", userId);

        try {
            UserStats stats = reportService.collectStats(userId);
            TravelStyle style = travelStyleCalculator.calculate(stats);

            // 수정된 부분
            UserInsightReport aiReport = reportService.regenerateAiAnalysis(userId, stats, style);

            RegenerateResDto response = RegenerateResDto.builder()
                    .aiAnalysis(aiReport.getAiAnalysis())
                    .aiRecommendation(aiReport.getAiRecommendation())
                    .build();

            log.info("AI 분석 재생성 완료: userId={}", userId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("AI 분석 재생성 실패: userId={}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}