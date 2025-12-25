package com.gt.planit.domain.news.controller;

import com.gt.planit.domain.news.model.dto.NewsArticleDto;
import com.gt.planit.domain.news.model.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/news/keywords")
@RequiredArgsConstructor
@Slf4j
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    /**
     * spotId 기준 뉴스 조회
     */
    @GetMapping("/{spotId}")
    public List<NewsArticleDto> getNewsBySpot(
            @PathVariable Long spotId,
            @RequestParam String spotName
    ) {
        return newsArticleService.getOrCreateNews(spotId, spotName);
    }

    /**
     * 뉴스 삭제 (관리자)
     */
    @DeleteMapping("/{articleId}")
    public void deleteNews(
            @PathVariable Long articleId
    ) {
        newsArticleService.deleteNews(articleId);
    }
}