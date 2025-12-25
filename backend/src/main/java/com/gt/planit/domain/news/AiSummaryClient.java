package com.gt.planit.domain.news;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.*;

@Component
@RequiredArgsConstructor
public class AiSummaryClient {

    @Value("${gms.key}")
    private String gmsKey;

    public String summarize(String input) {
        try {
            String prompt = """
아래 내용을 기반으로 뉴스 요약 문장을 생성해라.
3줄 이내로 작성하라.

%s
""".formatted(input);

            String body = """
{
  "model": "gpt-4o-mini",
  "messages": [
    { "role": "user", "content": "%s" }
  ]
}
""".formatted(escape(prompt));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gms.ssafy.io/gmsapi/api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + gmsKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("===== GMS AI RAW RESPONSE =====");
            System.out.println(response.body());
            System.out.println("================================");

            JSONObject json = new JSONObject(response.body());

            if (!json.has("choices")) {
                throw new RuntimeException(
                        "AI 응답에 choices 없음: " + response.body()
                );
            }

            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            throw new RuntimeException("AI 요약 실패", e);
        }
    }

    private String escape(String s) {
        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n")
                .replace("\t", " ")
                .replaceAll("[\\x00-\\x1F]", "");
    }
}