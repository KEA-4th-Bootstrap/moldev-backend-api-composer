package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TrendPostResponseDto(
        TrendPostDetailListResponseDto postInfo,
        TrendUserDetailListResponseDto userInfo
) {
    public static TrendPostResponseDto of(TrendPostDetailListResponseDto postInfo,
                                          TrendUserDetailListResponseDto userInfo){
        return TrendPostResponseDto.builder()
                .postInfo(postInfo)
                .userInfo(userInfo)
                .build();
    }
}
