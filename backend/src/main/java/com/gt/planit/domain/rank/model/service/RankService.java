package com.gt.planit.domain.rank.model.service;

import com.gt.planit.domain.rank.model.dto.SpotRankDto;
import com.gt.planit.domain.rank.model.mapper.RankMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final RankMapper spotMapper;

    public List<SpotRankDto> getRankedSpots(int limit, Long userId) {
        return spotMapper.selectRankedSpots(limit, userId);
    }

    public List<SpotRankDto> getRankedPopup(int limit, Long userId) {
        return spotMapper.selectRankedPopup(limit, userId);
    }
}
