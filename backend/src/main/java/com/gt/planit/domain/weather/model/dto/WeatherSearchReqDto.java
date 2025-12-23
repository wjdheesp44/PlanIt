// WeatherSearchReqDto.java
package com.gt.planit.domain.weather.model.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherSearchReqDto {
    private Long gugunId;
    private LocalDate startDate;
    private LocalDate endDate;
}