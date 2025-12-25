package com.gt.planit.domain.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmsOpenAiReqDto {
    private String model;
    private List<Message> messages;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;  // "developer" or "user"
        private String content;
    }
}