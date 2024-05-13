package org.bootstrap.apicomposer.domain.reply.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;

@Builder(access= AccessLevel.PRIVATE)
public record ReplyTotalResponseDto(
        ReplyDetailListResponseDto replyInfo,
        UserDetailListResponseDto userInfo
) {
    public static ReplyTotalResponseDto of(ReplyDetailListResponseDto replyInfo, UserDetailListResponseDto userInfo) {
        return ReplyTotalResponseDto.builder()
                .replyInfo(replyInfo)
                .userInfo(userInfo)
                .build();
    }
}
