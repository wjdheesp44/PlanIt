package com.gt.planit.domain.plan.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupShareLink {
    private Long id;
    private String shareToken;
    private GroupRole defaultRole;
    private String isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime expiresAt;
    private Long groupId;
}
