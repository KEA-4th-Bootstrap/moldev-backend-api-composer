package org.bootstrap.apicomposer.domain.post.vo;

import java.util.List;

public record CategoryPostVo(
        List<CompositionCategoryPostVo> postInfo,
        Long count
) {
}
