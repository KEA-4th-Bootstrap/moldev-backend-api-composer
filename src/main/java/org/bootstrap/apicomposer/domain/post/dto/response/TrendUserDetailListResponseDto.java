package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.vo.PostDetailWithRedisVo;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;

import java.util.List;

@Builder
public record TrendUserDetailListResponseDto(
        List<UserProfileVo> userList
) {
    public static TrendUserDetailListResponseDto of(List<UserProfileVo> userList) {
        return TrendUserDetailListResponseDto.builder()
                .userList(userList)
                .build();
    }
}