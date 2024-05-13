package org.bootstrap.apicomposer.domain.reply.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;

@Builder(access= AccessLevel.PRIVATE)
public record CommentTotalResponseDto(
        CommentDetailListResponseDto commentInfo,
        UserDetailListResponseDto userInfo
) {
    public static CommentTotalResponseDto of(CommentDetailListResponseDto commentInfo, UserDetailListResponseDto userInfo) {
        return CommentTotalResponseDto.builder()
                .commentInfo(commentInfo)
                .userInfo(userInfo)
                .build();
    }
}
