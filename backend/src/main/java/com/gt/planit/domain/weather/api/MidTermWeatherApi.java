package com.gt.planit.domain.weather.api;

import com.gt.planit.domain.weather.model.dto.MidTermApiResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class MidTermWeatherApi {

    @Value("${weather.api.mid-term.url}")
    private String apiUrl;

    @Value("${weather.api.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public MidTermApiResDto getMidTermForecast(String regId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        String tmFc;
        if (now.isBefore(LocalTime.of(18, 0))) {
            tmFc = today.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600";
        } else {
            tmFc = today.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "1800";
        }

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("dataType", "JSON")
                .queryParam("regId", regId)
                .queryParam("tmFc", tmFc)
                .toUriString();

        log.debug("중기예보 API 호출: {}", url);

        return restTemplate.getForObject(url, MidTermApiResDto.class);
    }
}
