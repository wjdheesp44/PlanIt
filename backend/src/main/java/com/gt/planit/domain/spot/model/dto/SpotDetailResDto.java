package com.gt.planit.domain.spot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotDetailResDto {
    private Long id;
    private String title;
    private String badge;
    private Double avgRating;
    private String reviewCount;
    private String date;
    private String doroAddr;
    private String jibunAddr;
    private Double latitude;
    private Double longitude;
    private String tel;
    private String homepage;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String description;
    private Long sidoId;

    @JsonProperty("isFavorite")
    private boolean favorite;

    List<SpotRes> hotSpots;
}