package com.gt.planit.domain.plan.model.dto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FolderResDto {
    private Long id;
    private String name;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FolderResDto from(FolderDto folder) {
        return FolderResDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .thumbnailUrl(folder.getThumbnailPath() != null
                        ? "/api/files/" + folder.getThumbnailPath()
                        : null)
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .build();
    }
}