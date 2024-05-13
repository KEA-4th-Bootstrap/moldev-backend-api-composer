package org.bootstrap.apicomposer.domain.user.dto.response;

public record UserDetailResponseDto(
        String profileImgUrl,
        String moldevId,
        String nickname,
        String islandName
) {
}
