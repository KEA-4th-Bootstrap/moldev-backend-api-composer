package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;

@Builder(access = AccessLevel.PRIVATE)
public record SearchPostsResponseDto(
        PostDetailListResponseDto postInfo,
        UserDetailListResponseDto userInfo
) {
    public static SearchPostsResponseDto of(PostDetailListResponseDto postInfo,
                                            UserDetailListResponseDto userInfo) {
        return SearchPostsResponseDto.builder()
                .postInfo(postInfo)
                .userInfo(userInfo)
                .build();
    }
}
