package com.gt.planit.domain.spot.model.mapper;

import com.gt.planit.domain.spot.model.dto.RegionRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionMapper {
    List<RegionRes> selectRegions();

}
