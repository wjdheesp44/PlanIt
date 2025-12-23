package com.gt.planit.domain.likes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MySpotLikeResDto {
    /** 스팟 ID */
    private Long id;

    /** 스팟 제목 */
    private String title;

    /** 콘텐츠 타입 */
    private String badge;

    /** 대표 이미지 */
    private String image1;

    /** 평균 평점 */
    private BigDecimal avgRating;

    /** 평점 개수 */
    private Integer ratingCount;

    /** 시작 시간 */
    private LocalDateTime startTime;

    /** 종료 시간 */
    private LocalDateTime endTime;

    /** 도로명 주소 */
    private String doroAddr;

    /** 지번 주소 */
    private String jibunAddr;

    /** 좋아요 여부 (항상 true) */
    private Boolean isFavorite;
}
