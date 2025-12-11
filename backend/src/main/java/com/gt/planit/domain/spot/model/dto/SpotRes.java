package com.gt.planit.domain.spot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class SpotRes {
    private Long id;
    private String name;
    private String badge;     // 관광지 / 축제 / 팝업
    private Double rating;
    private String time;      // "AM 9:00 - PM 6:00"
    private String location;
    private String tags;      // "#감천 #마을"
    private String image;

    @JsonProperty("isFavorite")
    private boolean favorite;
}