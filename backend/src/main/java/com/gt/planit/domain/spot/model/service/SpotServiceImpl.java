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
        normalizeCondition(condition);
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

    private void normalizeCondition(SpotSearchCondition condition) {

        // 1) 별점: null/빈 리스트/1~5 전체 선택이면 필터 해제
        List<Integer> ratings = condition.getRatings();
        if (ratings == null || ratings.isEmpty()) {
            condition.setRatings(null);
        } else {
            // 중복 제거 + 정렬
            List<Integer> distinctSorted = ratings.stream()
                    .distinct()
                    .sorted()
                    .toList();

            // 1~5 전체 선택인지 체크
            List<Integer> fullRatings = List.of(1, 2, 3, 4, 5);
            if (distinctSorted.equals(fullRatings)) {
                // 전체 선택이면 조건 안 거는 게 요구사항이므로 null 처리
                condition.setRatings(null);
            } else {
                condition.setRatings(distinctSorted);
            }
        }

        // 2) 타입: 아예 안 들어왔으면 전체 타입 허용으로 보고 null 처리
        if (condition.getTypes() == null || condition.getTypes().isEmpty()) {
            condition.setTypes(null);
        }
    }
}
