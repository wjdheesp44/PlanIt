package com.gt.planit.domain.report.model.mapper;

import com.gt.planit.domain.report.model.entity.UserInsightReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInsightReportMapper {

    /**
     * 사용자 리포트 조회
     */
    UserInsightReport getReportByUserId(@Param("userId") Long userId);

    /**
     * 리포트 저장 (insert or update)
     */
    void upsertReport(UserInsightReport report);
}