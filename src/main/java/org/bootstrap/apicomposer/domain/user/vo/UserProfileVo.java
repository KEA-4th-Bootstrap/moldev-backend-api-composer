package org.bootstrap.apicomposer.domain.user.vo;

import lombok.Builder;

@Builder
public record UserProfileVo(
        String name,
        String profile
) {
}
