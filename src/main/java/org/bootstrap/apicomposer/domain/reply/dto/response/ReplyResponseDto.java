package org.bootstrap.apicomposer.domain.reply.dto.response;

import java.time.LocalDateTime;

public record ReplyResponseDto(
        String id,
        Long memberId,
        Long postId,
        String content,
        String parentsId,
        LocalDateTime createdAt
) {
}
