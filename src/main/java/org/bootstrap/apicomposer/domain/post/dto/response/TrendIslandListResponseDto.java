package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record TrendIslandListResponseDto(
        List<TrendIslandResponseDto> trendIslands
) {
    public static TrendIslandListResponseDto of(List<TrendIslandResponseDto> trendIslands) {
        return TrendIslandListResponseDto.builder()
                .trendIslands(trendIslands)
                .build();
    }
}

