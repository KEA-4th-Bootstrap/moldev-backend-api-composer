package org.bootstrap.apicomposer.domain.post.vo;

public record SearchPostVo(
        Long memberId,
        Long postId,
        String title,
        String content,
        String thumbnail,
        String categoryType,
        String lastModifiedDate
) {
}
