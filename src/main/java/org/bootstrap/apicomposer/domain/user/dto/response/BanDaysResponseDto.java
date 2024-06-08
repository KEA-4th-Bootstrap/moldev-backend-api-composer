package org.bootstrap.apicomposer.domain.user.dto.response;

public record BanDaysResponseDto(
        Long reportId,
        Integer banDays
) {
}
