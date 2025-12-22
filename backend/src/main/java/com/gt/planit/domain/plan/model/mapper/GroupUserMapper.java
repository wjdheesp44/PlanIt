package com.gt.planit.domain.plan.model.mapper;

import com.gt.planit.domain.plan.model.entity.GroupRole;
import com.gt.planit.domain.plan.model.entity.GroupUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
@Mapper
public interface GroupUserMapper {
    Optional<GroupUser> findByGroupIdAndUserId(@Param("groupId") Long groupId,
                                               @Param("userId") Long userId);

    List<GroupUser> findAllByGroupId(@Param("groupId") Long groupId);

    List<GroupUser> findAllByUserId(@Param("userId") Long userId);

    void insert(GroupUser groupUser);

    void updateRole(@Param("id") Long id,
                    @Param("role") GroupRole role);

    void delete(@Param("id") Long id);

    void deleteByGroupId(@Param("groupId") Long groupId);

    boolean isOwner(@Param("groupId") Long groupId,
                    @Param("userId") Long userId);

    boolean canEdit(@Param("groupId") Long groupId,
                    @Param("userId") Long userId);

    boolean canView(@Param("groupId") Long groupId,
                    @Param("userId") Long userId);
}
