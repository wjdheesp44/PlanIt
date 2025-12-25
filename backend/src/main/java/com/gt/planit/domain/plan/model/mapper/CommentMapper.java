package com.gt.planit.domain.plan.model.mapper;

import com.gt.planit.domain.plan.model.dto.CommentResDto;
import com.gt.planit.domain.plan.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentResDto> findAllByGroupId(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int insert(Comment comment);

    Comment findById(@Param("commentId") Long commentId);

    int softDelete(@Param("commentId") Long commentId);
}
