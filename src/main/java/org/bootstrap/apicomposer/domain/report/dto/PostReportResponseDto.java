package org.bootstrap.apicomposer.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PostReportResponseDto(
        ReportResponseDto reportResponseDto,
        String postTitle
) {
public static PostReportResponseDto of(ReportResponseDto reportResponseDto, String postTitle) {
        return PostReportResponseDto.builder()
                .reportResponseDto(reportResponseDto)
                .postTitle(postTitle)
                .build();
    }
}
