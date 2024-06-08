package org.bootstrap.apicomposer.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.report.entity.ReportType;
import org.bootstrap.apicomposer.domain.report.vo.ReportResponseVo;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
public record ReportWithBandaysResponseDto(
    Long reportId,
    ReportType reportType,
    String reporterId,
    String reporteeId,
    Object contentId,
    String reason,
    LocalDate reportDate,
    LocalDate processDate,
    Integer banDays
) {
    public static ReportWithBandaysResponseDto of(ReportResponseVo reportResponseVo, Integer banDays) {
        return ReportWithBandaysResponseDto.builder()
                .reportId(reportResponseVo.reportId())
                .reportType(reportResponseVo.reportType())
                .reporterId(reportResponseVo.reporterId())
                .reporteeId(reportResponseVo.reporteeId())
                .contentId(reportResponseVo.contentId())
                .reason(reportResponseVo.reason())
                .reportDate(reportResponseVo.reportDate())
                .processDate(reportResponseVo.processDate())
                .banDays(banDays)
                .build();
    }
}
