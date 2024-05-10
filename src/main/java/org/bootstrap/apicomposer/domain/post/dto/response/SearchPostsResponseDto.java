package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;

@Builder(access = AccessLevel.PRIVATE)
public record SearchPostsResponseDto(
        PostDetailResponseDto postInfo,
        UserDetailResponseDto userInfo
) {
    public static SearchPostsResponseDto of(PostDetailResponseDto postInfo,
                                            UserDetailResponseDto userInfo) {
        return SearchPostsResponseDto.builder()
                .postInfo(postInfo)
                .userInfo(userInfo)
                .build();
    }
}
