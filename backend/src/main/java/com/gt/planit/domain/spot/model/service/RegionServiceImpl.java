package com.gt.planit.domain.spot.model.service;

import com.gt.planit.domain.spot.model.dto.RegionRes;
import com.gt.planit.domain.spot.model.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    @Override
    public List<RegionRes> getRegions() {
        return regionMapper.selectRegions();
    }

}
