package com.gt.planit.domain.plan.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;
    private String content;
    private String isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long groupId;
    private Long userId;
}