package com.gt.planit.domain.review.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReqDto {
    private Integer rating;
    private String content;
}

