package com.gt.planit.domain.plan.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupUser {
    private Long id;
    private GroupRole role;
    private String isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;
    private Long groupId;

    // 권한 체크 메서드들
    public boolean isOwner() {
        return role == GroupRole.OWNER;
    }

    public boolean canEdit() {
        return role == GroupRole.OWNER || role == GroupRole.EDITOR;
    }

    public boolean canView() {
        return true; // 모든 PlanUser는 볼 수 있음
    }

    public boolean canManageShare() {
        return role == GroupRole.OWNER;
    }
}