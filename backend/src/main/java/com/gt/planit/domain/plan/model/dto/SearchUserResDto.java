package com.gt.planit.domain.plan.model.dto;

import com.gt.planit.domain.plan.model.entity.GroupRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserResDto {
    private Long id;
    private String nickname;
    private String email;
}
