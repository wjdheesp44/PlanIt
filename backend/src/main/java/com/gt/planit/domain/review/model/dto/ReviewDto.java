package com.gt.planit.domain.review.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long id;
    private Integer rating;
    private String content;
    private String isDeleted;

    private Long userId;
    private Long spotId;
    private String nickname;

    private String createdAt;
    private LocalDateTime modifiedAt;

    private int helpfulCount;

    private List<String> images;

}
