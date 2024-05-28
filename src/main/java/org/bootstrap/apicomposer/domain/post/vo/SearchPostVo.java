package org.bootstrap.apicomposer.domain.post.vo;

import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.global.utils.MemberIdField;

public record SearchPostVo(
        Long id,
        Long memberId,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        String createDate,
        String lastModifiedDate,
        Long viewCount,
        String moldevId
) implements MemberIdField {
    @Override
    public Long getMemberId() {
        return this.memberId;
    }
}
