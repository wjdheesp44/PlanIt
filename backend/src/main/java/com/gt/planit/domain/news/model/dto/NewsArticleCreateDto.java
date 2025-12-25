package com.gt.planit.domain.news.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsArticleCreateDto {

    private String title;
    private Long spotId;
    private String summary;
    private String sourceUrl;
    private LocalDateTime publishedAt;
}