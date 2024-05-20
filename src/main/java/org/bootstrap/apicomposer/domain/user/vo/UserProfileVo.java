package org.bootstrap.apicomposer.domain.user.vo;

import lombok.Builder;

@Builder
public record UserProfileVo(
        Long memberId,
        String profileImgUrl,
        String moldevId,
        String nickname,
        String islandName
) {
}
