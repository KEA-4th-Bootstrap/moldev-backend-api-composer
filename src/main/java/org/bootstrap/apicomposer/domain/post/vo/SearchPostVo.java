package org.bootstrap.apicomposer.domain.post.vo;

import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.global.utils.MemberIdField;

import java.time.LocalDateTime;

public record SearchPostVo(
        Long postId,
        String title,
        String content,
        Long memberId,
        String thumbnail,
        CategoryType category,
        LocalDateTime createDate,
        LocalDateTime lastModifiedDate,
        Long viewCount,
        String moldevId
) implements MemberIdField {

    @Override
    public Long getMemberId() {
        return this.memberId;
    }
}
