package org.bootstrap.apicomposer.domain.reply.dto.response;

import org.bootstrap.apicomposer.domain.reply.vo.CommentReplyVo;

import java.util.List;

public record ReplyDetailListResponseDto(
        List<CommentReplyVo> replyList
) {
}
