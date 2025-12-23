package com.gt.planit.domain.plan.controller;

import com.gt.planit.domain.plan.model.service.GroupShareService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/public/groups")
@RequiredArgsConstructor
public class GroupPublicController {

    private final GroupShareService groupShareService;

    /**
     * 공유 링크로 그룹 접근
     */
    @PostMapping("/join/{shareToken}")
    public ResponseEntity<Long> joinByShareLink(
            @PathVariable String shareToken,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        Long groupId = groupShareService.joinGroupByShareLink(shareToken, userId);
        return ResponseEntity.ok(groupId);
    }
}