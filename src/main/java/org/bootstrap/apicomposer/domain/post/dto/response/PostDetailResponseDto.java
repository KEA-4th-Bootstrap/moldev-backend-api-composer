package org.bootstrap.apicomposer.domain.post.dto.response;

import org.bootstrap.apicomposer.domain.post.vo.PostInfoVo;

public record PostDetailResponseDto(
        Long memberId,
        Long postId,
        String title,
        String content,
        String thumbnail,
        String categoryType,
        String lastModifiedDate,
        Integer viewCount   //게시글 조회수
){
}
