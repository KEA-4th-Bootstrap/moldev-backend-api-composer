package org.bootstrap.apicomposer.domain.post.vo;

import org.bootstrap.apicomposer.domain.post.type.CategoryType;

public record CompositionCategoryPostVo(
        Long id,
        String title,
        String content,
        String thumbnail,
        CategoryType category
) {
}
