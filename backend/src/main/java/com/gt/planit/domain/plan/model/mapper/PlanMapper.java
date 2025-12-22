package com.gt.planit.domain.plan.model.mapper;

import com.gt.planit.domain.plan.model.dto.PlanDto;
import com.gt.planit.domain.plan.model.dto.PlanSpotResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlanMapper {
    /**
     * 그룹에 플랜(스팟) 추가
     */
    void insertPlan(PlanDto plan);

    /**
     * 플랜 조회 (단건)
     */
    Optional<PlanDto> findById(@Param("planId") Long planId);

    /**
     * 그룹의 플랜 목록 조회 (스팟 정보 포함)
     */
    List<PlanSpotResDto> findPlanSpotsByGroupId(
            @Param("groupId") Long groupId,
            @Param("userId") Long userId
    );

    /**
     * 플랜 수정 (메모, 순서)
     */
    void updatePlan(PlanDto plan);

    /**
     * 플랜 삭제 (soft delete)
     */
    void deletePlan(@Param("planId") Long planId);

    /**
     * 그룹 내 최대 순서 조회
     */
    Integer findMaxSortOrderByGroupId(@Param("groupId") Long groupId);

    /**
     * 그룹에 해당 스팟이 이미 있는지 확인
     */
    boolean existsByGroupIdAndSpotId(
            @Param("groupId") Long groupId,
            @Param("spotId") Long spotId
    );

    /**
     * 순서 변경 시 기존 순서들 업데이트
     */
    void updateSortOrderRange(
            @Param("groupId") Long groupId,
            @Param("startOrder") Integer startOrder,
            @Param("endOrder") Integer endOrder,
            @Param("increment") Integer increment
    );
}
