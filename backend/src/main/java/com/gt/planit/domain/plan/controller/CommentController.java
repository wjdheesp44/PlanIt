package com.gt.planit.domain.plan.controller;

import com.gt.planit.domain.plan.model.dto.CommentCreateReqDto;
import com.gt.planit.domain.plan.model.dto.CommentResDto;
import com.gt.planit.domain.plan.model.service.CommentService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/groups/{groupId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResDto>> getComments(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        return ResponseEntity.ok(commentService.getComments(groupId, userId));
    }

    @PostMapping
    public ResponseEntity<Void> addComment(
            @PathVariable Long groupId,
            @RequestBody CommentCreateReqDto req,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        commentService.addComment(groupId, req, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long groupId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        commentService.deleteComment(groupId, commentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}