package com.gt.planit.domain.spot.controller;

import com.gt.planit.domain.spot.model.dto.RegionRes;
import com.gt.planit.domain.spot.model.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/v1/regions")
    public ResponseEntity<List<RegionRes>> getRegions() {
        List<RegionRes> regions = regionService.getRegions();
        return ResponseEntity.ok(regions);
    }

}
