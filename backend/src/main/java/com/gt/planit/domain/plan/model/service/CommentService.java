package com.gt.planit.domain.plan.model.service;

import com.gt.planit.domain.plan.model.dto.CommentCreateReqDto;
import com.gt.planit.domain.plan.model.dto.CommentResDto;
import com.gt.planit.domain.plan.model.entity.Comment;
import com.gt.planit.domain.plan.model.mapper.CommentMapper;
import com.gt.planit.domain.plan.model.mapper.GroupUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final GroupUserMapper groupUserMapper;

    /**
     * 댓글 목록 조회 (OWNER/EDITOR/VIEWER 모두 가능: canView)
     */
    public List<CommentResDto> getComments(Long groupId, Long currentUserId) {
        validateViewPermission(groupId, currentUserId);
        return commentMapper.findAllByGroupId(groupId);
    }

    /**
     * 댓글 작성 (OWNER/EDITOR/VIEWER 모두 가능: canView)
     */
    @Transactional
    public void addComment(Long groupId, CommentCreateReqDto req, Long currentUserId) {
        validateViewPermission(groupId, currentUserId);

        Comment comment = Comment.builder()
                .groupId(groupId)
                .userId(currentUserId)
                .content(req.getContent())
                .isDeleted("F")
                .build();

        commentMapper.insert(comment);
        log.info("Comment created: groupId={}, userId={}", groupId, currentUserId);
    }

    /**
     * 댓글 삭제: 기본 정책 (본인 댓글만) + OWNER는 전체 삭제 가능
     */
    @Transactional
    public void deleteComment(Long groupId, Long commentId, Long currentUserId) {
        validateViewPermission(groupId, currentUserId);

        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다");
        }

        if (!comment.getGroupId().equals(groupId)) {
            throw new SecurityException("잘못된 접근입니다");
        }

        boolean isOwner = groupUserMapper.isOwner(groupId, currentUserId);
        boolean isAuthor = comment.getUserId().equals(currentUserId);

        if (!isOwner && !isAuthor) {
            throw new SecurityException("삭제 권한이 없습니다");
        }

        commentMapper.softDelete(commentId);
        log.info("Comment deleted: commentId={}, byUser={}", commentId, currentUserId);
    }

    // === 권한 검증 ===
    private void validateViewPermission(Long groupId, Long userId) {
        if (!groupUserMapper.canView(groupId, userId)) {
            throw new SecurityException("조회 권한이 없습니다");
        }
    }
}
