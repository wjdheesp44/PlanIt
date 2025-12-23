package com.gt.planit.domain.likes.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewLike {

    private Long id;

    private String isDeleted;   // 'T' or 'F'

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long userId;

    private Long reviewId;
}