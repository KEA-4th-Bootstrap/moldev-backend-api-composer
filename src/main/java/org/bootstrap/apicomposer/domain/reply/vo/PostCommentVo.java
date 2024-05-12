package org.bootstrap.apicomposer.domain.reply.vo;

import java.time.LocalDateTime;

public record PostCommentVo(
        String id,
        Long memberId,
        String content,
        LocalDateTime createdAt
) {
}
