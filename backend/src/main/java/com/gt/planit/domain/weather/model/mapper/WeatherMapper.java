// WeatherMapper.java
package com.gt.planit.domain.weather.model.mapper;

import com.gt.planit.domain.weather.model.entity.WeatherForecast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface WeatherMapper {

    void insertOrUpdateWeatherForecast(WeatherForecast forecast);

    List<WeatherForecast> selectWeatherForecastsByDateRange(
            @Param("gugunId") Long gugunId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<WeatherForecast> selectWeatherForecastsByDate(
            @Param("gugunId") Long gugunId,
            @Param("date") LocalDate date
    );

    List<Map<String, Object>> selectAllGugunsWithGrid();

    List<Long> selectGugunIdsByMidLandRegId(@Param("midLandRegId") String midLandRegId);

    List<Map<String, Object>> selectDistinctMidRegIds();
}