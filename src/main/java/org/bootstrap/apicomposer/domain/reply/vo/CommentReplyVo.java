package org.bootstrap.apicomposer.domain.reply.vo;

import org.bootstrap.apicomposer.global.utils.MemberIdField;

import java.time.LocalDateTime;

public record CommentReplyVo(
        String id,
        Long memberId,
        String content,
        String parentsId,
        LocalDateTime createdAt
) implements MemberIdField {

    @Override
    public Long getMemberId() {
        return this.memberId;
    }
}
