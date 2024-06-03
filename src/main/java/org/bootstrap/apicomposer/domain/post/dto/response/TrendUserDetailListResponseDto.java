package org.bootstrap.apicomposer.domain.post.dto.response;

import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;

import java.util.List;

public record TrendUserDetailListResponseDto(
        List<UserProfileVo> userList
) {
}
