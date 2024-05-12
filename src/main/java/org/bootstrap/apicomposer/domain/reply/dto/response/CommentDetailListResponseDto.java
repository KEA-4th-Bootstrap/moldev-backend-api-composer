package org.bootstrap.apicomposer.domain.reply.dto.response;

import org.bootstrap.apicomposer.domain.reply.vo.PostCommentVo;
import org.bootstrap.apicomposer.global.common.PageInfo;

import java.util.List;

public record CommentDetailListResponseDto(
        List<PostCommentVo> commentList,
        PageInfo pageInfo
) {
}
