package com.gt.planit.domain.plan.model.service;

import com.gt.planit.domain.plan.model.dto.GroupShareLinkResDto;
import com.gt.planit.domain.plan.model.dto.GroupShareReqDto;
import com.gt.planit.domain.plan.model.dto.GroupUserResDto;
import com.gt.planit.domain.plan.model.entity.GroupRole;
import com.gt.planit.domain.plan.model.entity.GroupShareLink;
import com.gt.planit.domain.plan.model.entity.GroupUser;
import com.gt.planit.domain.plan.model.mapper.FolderMapper;
import com.gt.planit.domain.plan.model.mapper.GroupShareLinkMapper;
import com.gt.planit.domain.plan.model.mapper.GroupUserMapper;
import com.gt.planit.domain.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupShareService {

    private final GroupUserMapper groupUserMapper;
    private final GroupShareLinkMapper groupShareLinkMapper;
    private final FolderMapper folderMapper;
    private final UserMapper userMapper;

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    /**
     * 그룹 참여자 목록 조회
     */
    public List<GroupUserResDto> getGroupUsers(Long groupId, Long currentUserId) {
        validateViewPermission(groupId, currentUserId);

        List<GroupUser> groupUsers = groupUserMapper.findAllByGroupId(groupId);

        return groupUsers.stream()
                .map(groupUser -> {
                    var user = userMapper.findById(groupUser.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
                    return GroupUserResDto.of(groupUser, user.getNickname(), user.getEmail());
                })
                .collect(Collectors.toList());
    }

    /**
     * 유저를 그룹에 추가 (직접 공유)
     */
    @Transactional
    public GroupUserResDto addUserToGroup(Long groupId, GroupShareReqDto request, Long currentUserId) {
        validateOwnerPermission(groupId, currentUserId);

        folderMapper.findById(groupId, currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없습니다"));
        log.info("???: {} {}", request.getUserId(), request.getRole());
        var user = userMapper.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        groupUserMapper.findByGroupIdAndUserId(groupId, request.getUserId())
                .ifPresent(gu -> {
                    throw new IllegalStateException("이미 그룹에 참여 중입니다");
                });

        if (request.getRole() == GroupRole.OWNER) {
            throw new IllegalArgumentException("OWNER 권한은 직접 부여할 수 없습니다");
        }

        GroupUser groupUser = GroupUser.builder()
                .groupId(groupId)
                .userId(request.getUserId())
                .role(request.getRole())
                .isDeleted("F")
                .build();

        groupUserMapper.insert(groupUser);
        log.info("User added to group: groupId={}, userId={}, role={}", groupId, request.getUserId(), request.getRole());

        return GroupUserResDto.of(groupUser, user.getNickname(), user.getEmail());
    }

    /**
     * 그룹 참여자 권한 변경
     */
    @Transactional
    public void updateUserRole(Long groupId, Long groupUserId, GroupRole newRole, Long currentUserId) {
        validateOwnerPermission(groupId, currentUserId);

        if (newRole == GroupRole.OWNER) {
            throw new IllegalArgumentException("OWNER 권한으로 변경할 수 없습니다");
        }

        groupUserMapper.updateRole(groupUserId, newRole);
        log.info("Group user role updated: groupUserId={}, newRole={}", groupUserId, newRole);
    }

    /**
     * 그룹 참여자 제거
     */
    @Transactional
    public void removeUserFromGroup(Long groupId, Long groupUserId, Long currentUserId) {
        validateOwnerPermission(groupId, currentUserId);

        groupUserMapper.delete(groupUserId);
        log.info("User removed from group: groupUserId={}", groupUserId);
    }

    /**
     * 공유 링크 생성
     */
    @Transactional
    public GroupShareLinkResDto createShareLink(Long groupId, Long currentUserId) {
        validateOwnerPermission(groupId, currentUserId);

        folderMapper.findById(groupId, currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없습니다"));

        return groupShareLinkMapper.findActiveByGroupId(groupId)
                .map(link -> GroupShareLinkResDto.of(link, frontendUrl))
                .orElseGet(() -> {
                    GroupShareLink shareLink = GroupShareLink.builder()
                            .groupId(groupId)
                            .shareToken(UUID.randomUUID().toString())
                            .defaultRole(GroupRole.VIEWER)
                            .isDeleted("F")
                            .build();

                    groupShareLinkMapper.insert(shareLink);
                    log.info("Share link created: groupId={}, token={}", groupId, shareLink.getShareToken());
                    return GroupShareLinkResDto.of(shareLink, frontendUrl);
                });
    }

    /**
     * 공유 링크로 그룹 접근
     */
    @Transactional
    public Long joinGroupByShareLink(String shareToken, Long userId) {
        GroupShareLink shareLink = groupShareLinkMapper.findByShareToken(shareToken)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 공유 링크입니다"));

        Long groupId = shareLink.getGroupId();

        var existingGroupUser = groupUserMapper.findByGroupIdAndUserId(groupId, userId);
        if (existingGroupUser.isPresent()) {
            return groupId;
        }

        GroupUser groupUser = GroupUser.builder()
                .groupId(groupId)
                .userId(userId)
                .role(shareLink.getDefaultRole())
                .isDeleted("F")
                .build();

        groupUserMapper.insert(groupUser);
        log.info("User joined group by link: groupId={}, userId={}, role={}", groupId, userId, shareLink.getDefaultRole());

        return groupId;
    }

    /**
     * 공유 링크 비활성화
     */
    @Transactional
    public void deactivateShareLink(Long groupId, Long currentUserId) {
        validateOwnerPermission(groupId, currentUserId);
        groupShareLinkMapper.deactivateAllByGroupId(groupId);
        log.info("Share links deactivated: groupId={}", groupId);
    }

    // === 권한 검증 메서드들 ===

    private void validateOwnerPermission(Long groupId, Long userId) {
        if (!groupUserMapper.isOwner(groupId, userId)) {
            throw new SecurityException("권한이 없습니다 (OWNER만 가능)");
        }
    }

    private void validateEditPermission(Long groupId, Long userId) {
        if (!groupUserMapper.canEdit(groupId, userId)) {
            throw new SecurityException("편집 권한이 없습니다");
        }
    }

    private void validateViewPermission(Long groupId, Long userId) {
        if (!groupUserMapper.canView(groupId, userId)) {
            throw new SecurityException("조회 권한이 없습니다");
        }
    }
}