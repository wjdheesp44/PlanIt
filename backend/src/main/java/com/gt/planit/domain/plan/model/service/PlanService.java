package com.gt.planit.domain.plan.model.service;

import com.gt.planit.domain.plan.model.dto.PlanSpotResDto;
import com.gt.planit.domain.plan.model.dto.UpdatePlanReqDto;

import java.util.List;

public interface PlanService {
    public Long addSpotToGroup(Long groupId, Long spotId, Long userId, String memo);

    public List<PlanSpotResDto> getGroupSpots(Long groupId, Long userId);

    public void updatePlan(Long planId, Long userId, UpdatePlanReqDto request);

    public void deletePlan(Long planId, Long userId);

}
