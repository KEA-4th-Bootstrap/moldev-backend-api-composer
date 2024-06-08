package org.bootstrap.apicomposer.domain.user.dto.response;

import java.util.List;

public record BanDaysListResponseDto(
        List<BanDaysResponseDto> banDaysResponseDto
) {
}
