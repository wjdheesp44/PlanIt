package com.gt.planit.domain.spot.model.service;

import com.gt.planit.domain.spot.model.dto.SpotRes;
import com.gt.planit.domain.spot.model.dto.SpotSearchCondition;
import com.gt.planit.domain.spot.model.mapper.SpotMapper;
import com.gt.planit.global.response.PageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotMapper spotMapper;

    @Override
    public PageRes<SpotRes> searchSpots(SpotSearchCondition condition, int page, int size) {
        int pageIndex = Math.max(page, 1) - 1;
        int offset = pageIndex * size;

        List<SpotRes> data = spotMapper.searchSpots(condition, offset, size);
        long totalElements = spotMapper.countSpots(condition);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean last = (pageIndex + 1) >= totalPages;

        return PageRes.<SpotRes>builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(last)
                .data(data)
                .build();
    }
}
