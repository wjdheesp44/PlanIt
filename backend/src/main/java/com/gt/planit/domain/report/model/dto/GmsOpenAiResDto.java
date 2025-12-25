package com.gt.planit.domain.report.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GmsOpenAiResDto {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
        private Integer index;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}