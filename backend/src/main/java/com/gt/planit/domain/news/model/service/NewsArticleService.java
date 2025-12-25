package com.gt.planit.domain.news.model.service;

import com.gt.planit.domain.news.AiSummaryClient;
import com.gt.planit.domain.news.NaverBlogClient;
import com.gt.planit.domain.news.model.dto.NewsArticleCreateDto;
import com.gt.planit.domain.news.model.dto.NewsArticleDto;
import com.gt.planit.domain.news.model.mapper.NewsArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleMapper newsArticleMapper;
    private final NaverBlogClient naverBlogClient;
    private final AiSummaryClient aiSummaryClient;

    public List<NewsArticleDto> getNewsBySpot(Long spotId) {
        return newsArticleMapper.findBySpotId(spotId);
    }

    public boolean hasNews(Long spotId) {
        return newsArticleMapper.existsBySpotId(spotId);
    }

    public void saveNews(NewsArticleCreateDto dto) {
        newsArticleMapper.insert(dto);
    }

    public void deleteNews(Long articleId) {
        newsArticleMapper.softDelete(articleId);
    }

    public List<NewsArticleDto> getOrCreateNews(Long spotId, String spotName) {

        if (newsArticleMapper.existsBySpotId(spotId)) {
            return newsArticleMapper.findBySpotId(spotId);
        }

        // 1. 네이버 블로그 수집
        String blogJson = naverBlogClient.searchBlog(spotName);

        // 2. AI 요약
        String summary = aiSummaryClient.summarize(blogJson);

        // 3. 저장
        NewsArticleCreateDto dto = new NewsArticleCreateDto();
        dto.setSpotId(spotId);
        dto.setTitle(spotName + " 관련 소식");
        dto.setSummary(summary);
        dto.setSourceUrl("NAVER");
        dto.setPublishedAt(java.time.LocalDateTime.now());

        newsArticleMapper.insert(dto);

        // 4. 다시 조회
        return newsArticleMapper.findBySpotId(spotId);
    }

}