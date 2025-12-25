package com.gt.planit.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyReviewResDto {
    private Long id;
    private String title;
    private String badge;
    private String date;
    private String location;
    private int rating;
    private String text;
    private String image;
    private String reviewImages;
    public List<String> getImages() {
        if (reviewImages == null) return List.of();
        return Arrays.asList(reviewImages.split(","));
    }
}
