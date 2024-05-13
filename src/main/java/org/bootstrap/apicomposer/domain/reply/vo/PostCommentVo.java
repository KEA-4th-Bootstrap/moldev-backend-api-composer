package org.bootstrap.apicomposer.domain.reply.vo;

import java.time.LocalDateTime;

public record PostCommentVo(
        String id,
        Long memberId,
        Long postId,
        String content,
        Long replyCount,
        LocalDateTime createdAt
) {
}
