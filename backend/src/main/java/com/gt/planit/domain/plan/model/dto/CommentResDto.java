package com.gt.planit.domain.plan.model.dto;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResDto {
    private Long id;
    private String author;
    private LocalDateTime createdAt;
    private String content;
    private Boolean isAuthor;
}
