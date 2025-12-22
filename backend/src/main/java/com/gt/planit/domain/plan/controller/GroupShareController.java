package com.gt.planit.domain.plan.controller;

import com.gt.planit.domain.plan.model.dto.GroupShareLinkResDto;
import com.gt.planit.domain.plan.model.dto.GroupShareReqDto;
import com.gt.planit.domain.plan.model.dto.GroupUserResDto;
import com.gt.planit.domain.plan.model.entity.GroupRole;
import com.gt.planit.domain.plan.model.service.GroupShareService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/groups/{groupId}/share")
@RequiredArgsConstructor
public class GroupShareController {
    private final GroupShareService groupShareService;

    /**
     * 그룹 참여자 목록 조회
     */
    @GetMapping("/users")
    public ResponseEntity<List<GroupUserResDto>> getGroupUsers(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        List<GroupUserResDto> users = groupShareService.getGroupUsers(groupId, userId);
        return ResponseEntity.ok(users);
    }

    /**
     * 유저를 그룹에 추가
     */
    @PostMapping("/users")
    public ResponseEntity<GroupUserResDto> addUser(
            @PathVariable Long groupId,
            @RequestBody GroupShareReqDto request,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        GroupUserResDto response = groupShareService.addUserToGroup(groupId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 참여자 권한 변경
     */
    @PatchMapping("/users/{groupUserId}/role")
    public ResponseEntity<Void> updateUserRole(
            @PathVariable Long groupId,
            @PathVariable Long groupUserId,
            @RequestParam GroupRole role,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        groupShareService.updateUserRole(groupId, groupUserId, role, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 참여자 제거
     */
    @DeleteMapping("/users/{groupUserId}")
    public ResponseEntity<Void> removeUser(
            @PathVariable Long groupId,
            @PathVariable Long groupUserId,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        groupShareService.removeUserFromGroup(groupId, groupUserId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 공유 링크 생성
     */
    @PostMapping("/link")
    public ResponseEntity<GroupShareLinkResDto> createShareLink(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        GroupShareLinkResDto response = groupShareService.createShareLink(groupId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 공유 링크 비활성화
     */
    @DeleteMapping("/link")
    public ResponseEntity<Void> deactivateShareLink(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        groupShareService.deactivateShareLink(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}