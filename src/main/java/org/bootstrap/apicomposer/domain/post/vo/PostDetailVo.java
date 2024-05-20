package org.bootstrap.apicomposer.domain.post.vo;

import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;

@Builder
public record PostDetailVo(
        Long id,
        String moldevId,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        Integer viewCount
) {
}