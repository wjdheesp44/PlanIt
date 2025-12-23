package com.gt.planit.domain.plan.model.dto;

import com.gt.planit.domain.plan.model.entity.GroupShareLink;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroupShareLinkResDto {
    private Long id;
    private String shareToken;
    private String shareUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public static GroupShareLinkResDto of(GroupShareLink shareLink, String baseUrl) {
        return GroupShareLinkResDto.builder()
                .id(shareLink.getId())
                .shareToken(shareLink.getShareToken())
                .shareUrl(baseUrl + "/shared/plan/" + shareLink.getShareToken())
                .createdAt(shareLink.getCreatedAt())
                .expiresAt(shareLink.getExpiresAt())
                .build();
    }
}
