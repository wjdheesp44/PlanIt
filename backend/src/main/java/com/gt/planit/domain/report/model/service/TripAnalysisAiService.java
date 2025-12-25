package com.gt.planit.domain.report.model.service;


import com.gt.planit.domain.report.model.entity.TravelStyle;
import com.gt.planit.domain.report.model.entity.UserStats;
import com.gt.planit.domain.report.util.GmsOpenAiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripAnalysisAiService {

    private final GmsOpenAiClient gmsOpenAiClient;

    /**
     * 여행 스타일 분석 생성
     */
    public String generateTravelStyleAnalysis(UserStats stats, TravelStyle style) {
        String systemPrompt = "당신은 여행 분석 전문가입니다. 사용자의 데이터를 바탕으로 친근하고 격려하는 톤으로 여행 스타일을 분석해주세요.";

        String userPrompt = buildAnalysisPrompt(stats, style);

        return gmsOpenAiClient.call(systemPrompt, userPrompt);
    }

    /**
     * AI 추천 생성
     */
    public String generateRecommendation(UserStats stats, TravelStyle style) {
        String systemPrompt = "당신은 여행 추천 전문가입니다. 한국의 실제 관광지를 바탕으로 구체적이고 실용적인 추천을 해주세요.";

        String userPrompt = buildRecommendationPrompt(stats, style);

        return gmsOpenAiClient.call(systemPrompt, userPrompt);
    }

    private String buildAnalysisPrompt(UserStats stats, TravelStyle style) {
        return String.format("""
            ## 사용자 프로필
            - 여행 스타일: %s (%s)
            - 선호 지역: %s (%d개 스팟, %.1f%%)
            - 선호 콘텐츠: %s (%.1f%%)
            - 총 저장 스팟: %d개
            - 작성 리뷰: %d개 (평균 %.1f점)
            - 좋아요한 스팟: %d개
            - 주요 해시태그: %s
            
            ## 요청사항
            위 데이터를 바탕으로 2-3문단으로 여행 스타일 분석을 작성해주세요.
            
            1문단: 사용자의 여행 스타일 특징 설명 (%s 스타일 언급)
            2문단: 주요 데이터를 바탕으로 한 구체적인 인사이트
            3문단: 앞으로의 여행 방향 제안
            
            친근하고 격려하는 톤으로 작성하되, 과도한 칭찬은 피해주세요.
            """,
                style.getName(),
                style.getDescription(),
                stats.getTopRegion() != null ? stats.getTopRegion().getSidoName() : "정보 없음",
                stats.getTopRegion() != null ? stats.getTopRegion().getCount() : 0,
                stats.getTopRegion() != null ? stats.getTopRegion().getPercentage() : 0.0,
                stats.getTopContentType() != null ? stats.getTopContentType().getContentType() : "정보 없음",
                stats.getTopContentType() != null ? stats.getTopContentType().getPercentage() : 0.0,
                stats.getTotalSpots() != null ? stats.getTotalSpots() : 0,
                stats.getTotalReviews() != null ? stats.getTotalReviews() : 0,
                stats.getAvgRating() != null ? stats.getAvgRating() : 0.0,
                stats.getTotalLikes() != null ? stats.getTotalLikes() : 0,
                stats.getTopHashtags() != null ? String.join(", ", stats.getTopHashtags()) : "없음",
                style.getName()
        );
    }

    private String buildRecommendationPrompt(UserStats stats, TravelStyle style) {
        String secondRegion = stats.getRegions() != null && stats.getRegions().size() > 1
                ? stats.getRegions().get(1).getSidoName()
                : "다양한 지역";
        double secondPercentage = stats.getRegions() != null && stats.getRegions().size() > 1
                ? stats.getRegions().get(1).getPercentage()
                : 0.0;

        return String.format("""
            ## 사용자 정보
            - 여행 스타일: %s
            - 주로 방문한 지역: %s (%.1f%%)
            - 두 번째로 많은 지역: %s (%.1f%%)
            - 선호 콘텐츠: %s (%.1f%%)
            
            ## 요청사항
            위 정보를 바탕으로 2-3줄로 구체적인 여행지 추천을 해주세요.
            - 실제 존재하는 한국의 관광지 이름을 구체적으로 언급
            - 사용자가 아직 가보지 않은 새로운 지역 위주로 추천
            - 친근하고 설득력 있는 톤으로 작성
            
            예시: "주로 서울의 관광지를 즐기시는군요! 비슷한 역사적 분위기의 경주 불국사와 양동마을을 추천드립니다. 다음 여행은 경상도 지역을 탐험해보는 건 어떨까요?"
            """,
                style.getName(),
                stats.getTopRegion() != null ? stats.getTopRegion().getSidoName() : "정보 없음",
                stats.getTopRegion() != null ? stats.getTopRegion().getPercentage() : 0.0,
                secondRegion,
                secondPercentage,
                stats.getTopContentType() != null ? stats.getTopContentType().getContentType() : "정보 없음",
                stats.getTopContentType() != null ? stats.getTopContentType().getPercentage() : 0.0
        );
    }
}
