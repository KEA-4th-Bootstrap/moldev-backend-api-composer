package org.bootstrap.apicomposer.domain.user.dto.response;

import lombok.Builder;
import org.bootstrap.apicomposer.domain.user.vo.SearchUserVo;

import java.util.List;

@Builder
public record UserDetailResponseDto(
        List<SearchUserVo> userList
) {
}
