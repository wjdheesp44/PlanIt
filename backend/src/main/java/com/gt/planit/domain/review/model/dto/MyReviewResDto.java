package com.gt.planit.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyReviewResDto {
    private Long id;
    private String title;
    private String category;
    private String date;
    private String location;
    private int rating;
    private String text;
    private String image;
}
