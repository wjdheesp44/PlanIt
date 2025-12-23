package com.gt.planit.domain.weather.api;

import com.gt.planit.domain.weather.model.dto.ShortTermApiResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", gridX)
                .queryParam("ny", gridY)
                .toUriString();

        log.debug("단기예보 API 호출: {}", url);

        return restTemplate.getForObject(url, ShortTermApiResDto.class);
    }
}