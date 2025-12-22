package com.gt.planit.domain.plan.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {
    private Long id;
    private String memo;
    private Integer sortOrder;
    private String isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long groupId;
    private Long spotId;

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void delete() {
        this.isDeleted = "T";
    }
}
