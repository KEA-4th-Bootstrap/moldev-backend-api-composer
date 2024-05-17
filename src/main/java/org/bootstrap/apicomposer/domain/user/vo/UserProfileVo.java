package org.bootstrap.apicomposer.domain.user.vo;

import lombok.Builder;

@Builder
public record UserProfileVo(
        String profileImgUrl,
        String moldevId,
        String nickname,
        String islandName
) {
}
