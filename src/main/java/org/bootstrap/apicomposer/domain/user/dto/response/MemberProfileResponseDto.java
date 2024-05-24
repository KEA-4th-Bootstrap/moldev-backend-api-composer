package org.bootstrap.apicomposer.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberProfileResponseDto(
        Long memberId,
        String profileImgUrl,
        String moldevId,
        String nickname,
        String islandName
) {
}
