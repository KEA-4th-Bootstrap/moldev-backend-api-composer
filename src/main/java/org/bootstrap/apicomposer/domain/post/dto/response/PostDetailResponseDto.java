package org.bootstrap.apicomposer.domain.post.dto.response;

import java.time.LocalDateTime;

public record PostDetailResponseDto(
        Long postId,
        String title,
        String content,
        String thumbnail,
        String categoryType,
        LocalDateTime lastModifiedDate,
        Integer viewCount   //게시글 조회수
) {
}
