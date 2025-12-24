package com.gt.planit.domain.rank.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpotRankDto {

    private Long id;
    private String title;
    private String contentType;
    private String image1;
    private String time;
    private String location;

    @JsonProperty("isFavorite")
    private boolean favorite;

    private int likeCount;
    private double avgRating;
    private int reviewCount;
}
