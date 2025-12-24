package com.gt.planit.domain.rank.model.mapper;

import com.gt.planit.domain.rank.model.dto.SpotRankDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RankMapper {
    List<SpotRankDto> selectRankedSpots(@Param("limit") int limit, @Param("userId") Long userId);
    List<SpotRankDto> selectRankedPopup(@Param("limit") int limit, @Param("userId") Long userId);
}
