package com.gt.planit.domain.plan.controller;

import com.gt.planit.domain.plan.model.dto.CreatePlanReqDto;
import com.gt.planit.domain.plan.model.dto.FolderDto;
import com.gt.planit.domain.plan.model.dto.PlanSpotResDto;
import com.gt.planit.domain.plan.model.dto.UpdatePlanReqDto;
import com.gt.planit.domain.plan.model.mapper.FolderMapper;
import com.gt.planit.domain.plan.model.service.PlanService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/groups/{groupId}")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final FolderMapper folderMapper;
    /**
     * 그룹에 스팟 추가
     * POST /api/v1/groups/{groupId}/spots/{spotId}
     */
    @PostMapping("/spots/{spotId}")
    public ResponseEntity<Map<String, Object>> addSpotToGroup(
            @PathVariable Long groupId,
            @PathVariable Long spotId,
            @RequestBody CreatePlanReqDto request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        log.info("POST /api/v1/groups/{}/plans - spotId: {}", groupId, spotId);

        Long planId = planService.addSpotToGroup(groupId, spotId, userId, request.getMemo());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "success", true,
                        "message", "스팟이 추가되었습니다.",
                        "data", Map.of("planId", planId)
                ));
    }

    /**
     * 그룹의 스팟 목록 조회
     * GET /api/v1/groups/{groupId}/plans
     */
    @GetMapping("/plans")
    public ResponseEntity<Map<String, Object>> getGroupSpots(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        log.info("GET /api/v1/groups/{}/plans", groupId);

        FolderDto group = folderMapper.findById(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없습니다."));

        List<PlanSpotResDto> spots = planService.getGroupSpots(groupId, userId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "스팟 목록 조회 성공",
                "data", Map.of(
                        "groupId", group.getId(),
                        "title", group.getName(),
                        "imageUrl", group.getThumbnailPath(),
                        "spots", spots
                )
        ));
    }

    /**
     * 플랜 수정 (메모, 순서)
     * PUT /api/v1/groups/{groupId}/plans/{planId}
     */
    @PutMapping("/plans/{planId}")
    public ResponseEntity<Map<String, Object>> updatePlan(
            @PathVariable Long groupId,
            @PathVariable Long planId,
            @RequestBody UpdatePlanReqDto request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        log.info("PUT /api/v1/groups/{}/plans/{} - memo: {}, sortOrder: {}",
                groupId, planId, request.getMemo(), request.getSortOrder());

        planService.updatePlan(planId, userId, request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "플랜이 수정되었습니다."
        ));
    }

    /**
     * 플랜 삭제
     * DELETE /api/v1/groups/{groupId}/plans/{planId}
     */
    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<Map<String, Object>> deletePlan(
            @PathVariable Long groupId,
            @PathVariable Long planId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        log.info("DELETE /api/v1/groups/{}/plans/{}", groupId, planId);

        planService.deletePlan(planId, userId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "플랜이 삭제되었습니다."
        ));
    }
}
