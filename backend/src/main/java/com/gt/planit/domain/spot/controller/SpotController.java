package com.gt.planit.domain.spot.controller;

import com.gt.planit.domain.spot.model.dto.SpotRes;
import com.gt.planit.domain.spot.model.dto.SpotSearchCondition;
import com.gt.planit.domain.spot.model.service.SpotService;
import com.gt.planit.global.response.PageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @GetMapping("/v1/spots")
    public ResponseEntity<PageRes<SpotRes>> getAllSpots(
            @RequestParam List<String> type,

            @RequestParam(name = "start-date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(name = "end-date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(name = "weather", required = false)
            List<String> weathers,

            @RequestParam(name = "rating", required = false)
            List<Integer> ratings,

            @RequestParam(name = "likes-count", required = false)
            Integer likesCountMin,

            @RequestParam(name = "search", required = false)
            String searchKeyword,

            @RequestParam(name = "tag", required = false)
            List<String> tags,

            @RequestParam(name = "region", required = false)
            List<Long> regionIds,

            @RequestParam(name = "sort", required = false, defaultValue = "LATEST")
            String sort,

            @RequestParam(name = "page", required = false, defaultValue = "0")
            int page,

            @RequestParam(name = "size", required = false, defaultValue = "20")
            int size
    ) {
        SpotSearchCondition condition = SpotSearchCondition.builder()
                .types(type)
                .startDate(startDate)
                .endDate(endDate)
                .weathers(weathers)       // ✅ 변경
                .ratings(ratings)
                .likesCountMin(likesCountMin)
                .searchKeyword(searchKeyword)
                .tags(tags)
                .regionIds(regionIds)
                .sort(sort)
                .build();

        PageRes<SpotRes> result = spotService.searchSpots(condition, page, size);
        return ResponseEntity.ok(result);
    }


//    @GetMapping("/v1/spots/test")
//    public ResponseEntity<PageRes<SpotRes>> getAllSpots() {
//
//        return ResponseEntity.ok();
//    }

}
