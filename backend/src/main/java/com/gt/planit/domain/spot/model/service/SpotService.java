package com.gt.planit.domain.spot.model.service;

import com.gt.planit.domain.spot.model.dto.SpotRes;
import com.gt.planit.domain.spot.model.dto.SpotSearchCondition;
import com.gt.planit.global.response.PageRes;

public interface SpotService {
    PageRes<SpotRes> searchSpots(SpotSearchCondition condition, int page, int size);
}
