package com.gt.planit.domain.likes.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotLikeDto {
    private Long id;
    private String isDeleted; // 'T' or 'F'
}
