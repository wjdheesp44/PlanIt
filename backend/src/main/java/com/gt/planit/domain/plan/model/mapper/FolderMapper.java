package com.gt.planit.domain.plan.model.mapper;

import com.gt.planit.domain.plan.model.dto.FolderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FolderMapper {
    void insert(FolderDto folderDto);

    Optional<FolderDto> findById(@Param("id") Long id);

    List<FolderDto> findByUserId(@Param("userId") Long userId);

    void update(FolderDto folderDto);

    void deleteById(@Param("id") Long id);
}
