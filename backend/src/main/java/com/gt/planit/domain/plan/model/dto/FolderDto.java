package com.gt.planit.domain.plan.model.dto;

import com.gt.planit.domain.plan.model.entity.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderDto {
    private Long id;
    private Long userId;
    private String name;
    private String thumbnailPath;
    private GroupRole userRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}