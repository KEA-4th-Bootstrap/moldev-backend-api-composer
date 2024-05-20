package org.bootstrap.apicomposer.domain.post.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
public record PostDetailWithRedisVo(
        PostDetailVo postInfo,
        Integer redisViewCount
) {
}