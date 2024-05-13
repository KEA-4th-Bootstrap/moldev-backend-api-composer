package org.bootstrap.apicomposer.domain.reply.vo;

import java.time.LocalDateTime;

public record CommentReplyVo(
        String id,
        Long memberId,
        String content,
        String parentsId,
        LocalDateTime createdAt
) {
}
