package com.gt.planit.domain.news;

import com.gt.planit.global.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NaverBlogClient {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    public String searchBlog(String keyword) {
        try {
            String query = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String url =
                    "https://openapi.naver.com/v1/search/blog?query=" + query +
                            "&display=5&sort=date";

            return HttpUtil.get(url, Map.of(
                    "X-Naver-Client-Id", clientId,
                    "X-Naver-Client-Secret", clientSecret
            ));
        } catch (Exception e) {
            throw new RuntimeException("네이버 블로그 검색 실패", e);
        }
    }
}