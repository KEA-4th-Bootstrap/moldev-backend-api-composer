package org.bootstrap.apicomposer.domain.post.vo;

public record PostInfoVo(
        Long memberId,
        Long postId,
        String title,
        String content,
        String thumbnail,
        String categoryType,
        String lastModifiedDate
) {
}
