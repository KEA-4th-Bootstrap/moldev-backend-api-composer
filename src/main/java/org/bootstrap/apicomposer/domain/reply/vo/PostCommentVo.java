package org.bootstrap.apicomposer.domain.reply.vo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostCommentVo(
        String id,
        Long memberId,
        Long postId,
        String content,
        Long replyCount,
        LocalDateTime createdAt
) {
    public static List<Long> getRequestMemberId(List<PostCommentVo> responseDtoList) {
        return responseDtoList.stream()
                .map(responseDto -> responseDto.memberId)
                .collect(Collectors.toList());
    }
}
