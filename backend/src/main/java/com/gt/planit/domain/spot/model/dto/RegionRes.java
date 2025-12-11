package com.gt.planit.domain.spot.model.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionRes {
    private Long id;
    private String sidoName;
    private List<GugunRes> guguns;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GugunRes {
        private Long id;
        private String gugunName;
    }
}
