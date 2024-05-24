package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.user.dto.response.TrendingMembersListResponseDto;

@Builder(access = AccessLevel.PRIVATE)
public record TrendIslandResponseDto(
        RecentPostsResponseListDto postInfo,
        TrendingMembersListResponseDto userInfo
) {
    public static TrendIslandResponseDto of(RecentPostsResponseListDto postInfo,
                                         TrendingMembersListResponseDto userInfo){
        return TrendIslandResponseDto.builder()
                .postInfo(postInfo)
                .userInfo(userInfo)
                .build();
    }

}
