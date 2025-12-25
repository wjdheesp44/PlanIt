package com.gt.planit.domain.report.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GmsOpenAiConfig {

    @Value("${gms.openai.key}")
    private String gmsKey;

    @Bean(name = "gmsOpenAiRestTemplate")
    public RestTemplate gmsOpenAiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 인터셉터로 헤더 자동 추가
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + gmsKey);
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}