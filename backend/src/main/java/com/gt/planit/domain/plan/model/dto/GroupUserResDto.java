package com.gt.planit.domain.plan.model.dto;

import com.gt.planit.domain.plan.model.entity.GroupRole;
import com.gt.planit.domain.plan.model.entity.GroupUser;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroupUserResDto {
    private Long id;
    private Long userId;
    private String nickname;
    private String email;
    private GroupRole role;
    private LocalDateTime createdAt;

    public static GroupUserResDto of(GroupUser groupUser, String nickname, String email) {
        return GroupUserResDto.builder()
                .id(groupUser.getId())
                .userId(groupUser.getUserId())
                .nickname(nickname)
                .email(email)
                .role(groupUser.getRole())
                .createdAt(groupUser.getCreatedAt())
                .build();
    }
}