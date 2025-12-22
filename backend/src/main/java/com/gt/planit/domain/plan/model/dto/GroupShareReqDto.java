package com.gt.planit.domain.plan.model.dto;

import com.gt.planit.domain.plan.model.entity.GroupRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupShareReqDto {
    private Long userId;
    private GroupRole role;  // EDITOR 또는 VIEWER
}
