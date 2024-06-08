package org.bootstrap.apicomposer.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.report.util.PageInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ReportWithBandaysListResponseDto(
        List<ReportWithBandaysResponseDto> reportList,
        PageInfo pageInfo
) {
    public static ReportWithBandaysListResponseDto of(List<ReportWithBandaysResponseDto> reportList, PageInfo pageInfo) {
        return ReportWithBandaysListResponseDto.builder()
                .reportList(reportList)
                .pageInfo(pageInfo)
                .build();
    }
}
