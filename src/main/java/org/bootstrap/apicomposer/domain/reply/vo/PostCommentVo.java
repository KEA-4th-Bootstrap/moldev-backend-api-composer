package org.bootstrap.apicomposer.domain.reply.vo;

import org.bootstrap.apicomposer.global.utils.MemberIdField;

import java.time.LocalDateTime;

public record PostCommentVo(
        String id,
        Long memberId,
        Long postId,
        String content,
        Long replyCount,
        LocalDateTime createdAt
) implements MemberIdField {

    @Override
    public Long getMemberId() {
        return this.memberId;
    }
}
