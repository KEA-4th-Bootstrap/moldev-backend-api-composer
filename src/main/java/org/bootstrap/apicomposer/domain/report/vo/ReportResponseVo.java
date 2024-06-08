package org.bootstrap.apicomposer.domain.report.vo;

import org.bootstrap.apicomposer.domain.report.entity.ReportType;

import java.time.LocalDate;

public record ReportResponseVo(
    Long reportId,
    ReportType reportType,
    String reporterId,
    String reporteeId,
    Object contentId,
    String reason,
    LocalDate reportDate,
    LocalDate processDate
) {
}
