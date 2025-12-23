package com.gt.planit.domain.plan.model.mapper;

import com.gt.planit.domain.plan.model.entity.GroupShareLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface GroupShareLinkMapper {
    void insert(GroupShareLink shareLink);

    Optional<GroupShareLink> findByShareToken(@Param("shareToken") String shareToken);

    Optional<GroupShareLink> findActiveByGroupId(@Param("groupId") Long groupId);

    void deactivate(@Param("id") Long id);

    void deactivateAllByGroupId(@Param("groupId") Long groupId);
}
