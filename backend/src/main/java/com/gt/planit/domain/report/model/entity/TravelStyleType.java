package com.gt.planit.domain.report.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TravelStyleType {
    CULTURAL_EXPLORER("문화 탐방가", "🏛️", "역사와 문화를 깊이 즐기는 진지한 여행자"),
    FESTIVAL_HUNTER("축제 헌터", "🎉", "전국의 축제를 찾아다니는 활동적인 탐험가"),
    TREND_SETTER("트렌드 세터", "🎨", "최신 트렌드를 놓치지 않는 센스있는 여행자"),
    ALL_ROUNDER("올라운더", "🗺️", "여행의 모든 요소를 즐기는 균형잡힌 여행자"),
    LOCAL_ENTHUSIAST("로컬 마니아", "🌟", "특정 지역을 깊이 파고드는 로컬 전문가"),
    EXPLORER("탐험가", "🚀", "새로운 곳을 끊임없이 찾아다니는 모험가"),
    BEGINNER("여행 입문자", "✈️", "여행을 시작하는 신선한 여행자");

    private final String name;
    private final String icon;
    private final String description;
}