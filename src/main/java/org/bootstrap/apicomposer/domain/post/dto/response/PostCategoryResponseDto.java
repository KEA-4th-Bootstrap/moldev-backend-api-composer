package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;

@Builder(access = AccessLevel.PRIVATE)
public record PostCategoryResponseDto(
        CategoryPostVo postInfo,
        UserProfileVo userInfo
) {
    public static PostCategoryResponseDto of(CategoryPostVo postInfo,
                                             UserProfileVo userInfo) {
        return PostCategoryResponseDto.builder()
                .postInfo(postInfo)
                .userInfo(userInfo)
                .build();
    }
}
