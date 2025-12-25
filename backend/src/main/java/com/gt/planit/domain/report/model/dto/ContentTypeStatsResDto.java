package com.gt.planit.domain.report.model.dto;

import com.gt.planit.domain.report.model.entity.ContentTypeCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentTypeStatsResDto {
    private String contentType;
    private Integer count;
    private Double percentage;

    public static ContentTypeStatsResDto from(ContentTypeCount contentTypeCount) {
        return ContentTypeStatsResDto.builder()
                .contentType(contentTypeCount.getContentType())
                .count(contentTypeCount.getCount())
                .percentage(contentTypeCount.getPercentage())
                .build();
    }

    public static List<ContentTypeStatsResDto> fromList(List<ContentTypeCount> contentTypes) {
        return contentTypes.stream()
                .map(ContentTypeStatsResDto::from)
                .collect(Collectors.toList());
    }
}