package org.bootstrap.apicomposer.domain.user.dto.response;

import org.bootstrap.apicomposer.domain.user.vo.SearchUserVo;

public record UserDetailResponseDto(
        String profileImgUrl,
        String moldevId,
        String nickname,
        String islandName
) {
}
