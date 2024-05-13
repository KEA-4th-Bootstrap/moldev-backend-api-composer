package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;

@Builder(access = AccessLevel.PRIVATE)
public record PostCategoryResponseDto(
        CategoryPostVo postInfo,
        UserDetailResponseDto userInfo
) {
    public static PostCategoryResponseDto of(CategoryPostVo postInfo,
                                             UserDetailResponseDto userInfo) {
        return PostCategoryResponseDto.builder()
                .postInfo(postInfo)
                .userInfo(userInfo)
                .build();
    }
}
