package org.bootstrap.apicomposer.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record BanDaysRequestDto(
        List<Long> reportIds
) {
    public static BanDaysRequestDto of(List<Long> reportIds) {
        return BanDaysRequestDto.builder()
                .reportIds(reportIds)
                .build();
    }
}
