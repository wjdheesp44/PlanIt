package com.gt.planit.domain.news.model.mapper;

import com.gt.planit.domain.news.model.dto.NewsArticleCreateDto;
import com.gt.planit.domain.news.model.dto.NewsArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsArticleMapper {

    List<NewsArticleDto> findBySpotId(@Param("spotId") Long spotId);

    boolean existsBySpotId(@Param("spotId") Long spotId);

    void insert(NewsArticleCreateDto dto);

    void softDelete(@Param("articleId") Long articleId);
}
