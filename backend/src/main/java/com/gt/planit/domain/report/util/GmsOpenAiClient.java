package com.gt.planit.domain.report.util;

import com.gt.planit.domain.report.model.dto.GmsOpenAiReqDto;
import com.gt.planit.domain.report.model.dto.GmsOpenAiResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GmsOpenAiClient {

    @Value("${gms.openai.url}")
    private String gmsOpenAiUrl;

    @Qualifier("gmsOpenAiRestTemplate")
    private final RestTemplate gmsOpenAiRestTemplate;

    /**
     * GMS OpenAI API 호출
     */
    public String call(String systemPrompt, String userPrompt) {
        try {
            // 요청 생성
            GmsOpenAiReqDto request = GmsOpenAiReqDto.builder()
                    .model("gpt-4o-mini")
                    .messages(List.of(
                            GmsOpenAiReqDto.Message.builder()
                                    .role("developer")
                                    .content(systemPrompt)
                                    .build(),
                            GmsOpenAiReqDto.Message.builder()
                                    .role("user")
                                    .content(userPrompt)
                                    .build()
                    ))
                    .build();

            log.debug("GMS OpenAI 호출 시작");

            // API 호출
            ResponseEntity<GmsOpenAiResDto> response = gmsOpenAiRestTemplate.postForEntity(
                    gmsOpenAiUrl,
                    request,
                    GmsOpenAiResDto.class
            );

            // 응답 파싱
            if (response.getBody() != null &&
                    !response.getBody().getChoices().isEmpty()) {
                String result = response.getBody()
                        .getChoices()
                        .get(0)
                        .getMessage()
                        .getContent();

                log.debug("GMS OpenAI 호출 성공");
                return result;
            }

            log.warn("GMS OpenAI 응답이 비어있음");
            return "AI 분석을 생성할 수 없습니다.";

        } catch (Exception e) {
            log.error("GMS OpenAI 호출 실패", e);
            return "AI 분석 생성 중 오류가 발생했습니다.";
        }
    }
}