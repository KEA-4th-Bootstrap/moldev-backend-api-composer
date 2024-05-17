package org.bootstrap.apicomposer.domain.reply.vo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentReplyVo(
        String id,
        Long memberId,
        String content,
        String parentsId,
        LocalDateTime createdAt
) {
    public static List<Long> getRequestMemberId(List<CommentReplyVo> responseDtoList) {
        return responseDtoList.stream()
                .map(responseDto -> responseDto.memberId)
                .collect(Collectors.toList());
    }
}
