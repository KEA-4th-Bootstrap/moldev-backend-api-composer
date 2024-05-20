package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.vo.PostDetailWithRedisVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record TrendPostDetailListResponseDto(
        List<PostDetailWithRedisVo> postList
) {
    public static TrendPostDetailListResponseDto of(List<PostDetailWithRedisVo> postList) {
        return TrendPostDetailListResponseDto.builder()
                .postList(postList)
                .build();
    }
}
