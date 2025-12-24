package com.gt.planit.domain.weather.api;

import com.gt.planit.domain.weather.model.dto.ShortTermApiResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ShortTermWeatherApi {

    @Value("${weather.api.short-term.url}")
    private String apiUrl;

    @Value("${weather.api.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ShortTermApiResDto getShortTermForecast(int gridX, int gridY) {
        LocalDate today = LocalDate.now();
        String baseDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = "0500";

        // UriComponentsBuilder에서 encode(false) 사용
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", serviceKey)  // 인코딩된 키 그대로 사용
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", gridX)
                .queryParam("ny", gridY)
                .build(true)  // false = 인코딩 하지 않음
                .toUri();

        log.debug("단기예보 API 호출: {}", uri);

        return restTemplate.getForObject(uri, ShortTermApiResDto.class);
    }
}