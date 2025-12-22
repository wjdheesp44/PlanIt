package com.gt.planit.domain.plan.model.service;


import com.gt.planit.domain.plan.model.dto.PlanDto;
import com.gt.planit.domain.plan.model.dto.PlanSpotResDto;
import com.gt.planit.domain.plan.model.dto.UpdatePlanReqDto;
import com.gt.planit.domain.plan.model.mapper.PlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanMapper planMapper;

    /**
     * 그룹에 스팟 추가
     */
    @Override
    @Transactional
    public Long addSpotToGroup(Long groupId, Long spotId, Long userId, String memo) {
        log.info("Adding spot to group - groupId: {}, spotId: {}", groupId, spotId);

        // 중복 체크
        boolean exists = planMapper.existsByGroupIdAndSpotId(groupId, spotId);
        if (exists) {
            throw new IllegalArgumentException("이미 추가된 스팟입니다.");
        }

        // 다음 순서 계산 (마지막 순서 + 1)
        Integer maxOrder = planMapper.findMaxSortOrderByGroupId(groupId);
        int nextOrder = (maxOrder == null ? 0 : maxOrder) + 1;

        // 플랜 생성
        PlanDto plan = PlanDto.builder()
                .groupId(groupId)
                .spotId(spotId)
                .memo(memo)
                .sortOrder(nextOrder)
                .isDeleted("F")
                .build();

        planMapper.insertPlan(plan);
        log.info("Spot added to group - planId: {}, sortOrder: {}", plan.getId(), nextOrder);

        return plan.getId();
    }

    /**
     * 그룹의 스팟 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlanSpotResDto> getGroupSpots(Long groupId, Long userId) {
        log.info("Getting group spots - groupId: {}, userId: {}", groupId, userId);

        List<PlanSpotResDto> spots = planMapper.findPlanSpotsByGroupId(groupId, userId);

        log.info("Found {} spots in group {}", spots.size(), groupId);
        return spots;
    }

    /**
     * 플랜 수정 (메모, 순서)
     */
    @Override
    @Transactional
    public void updatePlan(Long planId, Long userId, UpdatePlanReqDto request) {
        log.info("Updating plan - planId: {}, memo: {}, sortOrder: {}",
                planId, request.getMemo(), request.getSortOrder());

        PlanDto plan = planMapper.findById(planId);
        if (plan == null) {
            throw new IllegalArgumentException("존재하지 않는 플랜입니다.");
        }

        // 메모 업데이트
        if (request.getMemo() != null) {
            plan.updateMemo(request.getMemo());
        }

        // 순서 업데이트
        if (request.getSortOrder() != null) {
            updateSortOrder(plan, request.getSortOrder());
        } else {
            planMapper.updatePlan(plan);
        }

        log.info("Plan updated successfully - planId: {}", planId);
    }

    /**
     * 순서 변경
     */
    private void updateSortOrder(PlanDto plan, Integer newOrder) {
        Integer oldOrder = plan.getSortOrder();

        if (oldOrder.equals(newOrder)) {
            planMapper.updatePlan(plan);
            return;
        }

        Long groupId = plan.getGroupId();

        if (newOrder < oldOrder) {
            // 위로 이동: 새 위치 ~ 기존 위치-1 사이를 +1
            planMapper.updateSortOrderRange(groupId, newOrder, oldOrder - 1, 1);
        } else {
            // 아래로 이동: 기존 위치+1 ~ 새 위치 사이를 -1
            planMapper.updateSortOrderRange(groupId, oldOrder + 1, newOrder, -1);
        }

        // 현재 플랜의 순서 업데이트
        plan.updateSortOrder(newOrder);
        planMapper.updatePlan(plan);
    }

    /**
     * 플랜 삭제
     */
    @Override
    @Transactional
    public void deletePlan(Long planId, Long userId) {
        log.info("Deleting plan - planId: {}", planId);

        PlanDto plan = planMapper.findById(planId);
        if (plan == null) {
            throw new IllegalArgumentException("존재하지 않는 플랜입니다.");
        }

        // soft delete
        planMapper.deletePlan(planId);

        // 순서 재정렬 (삭제된 플랜 이후의 순서들을 -1)
        planMapper.updateSortOrderRange(
                plan.getGroupId(),
                plan.getSortOrder() + 1,
                999999,
                -1
        );

        log.info("Plan deleted successfully - planId: {}", planId);
    }

}
