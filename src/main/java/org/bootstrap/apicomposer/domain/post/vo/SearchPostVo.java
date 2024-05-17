package org.bootstrap.apicomposer.domain.post.vo;

import org.bootstrap.apicomposer.global.utils.MemberIdField;

public record SearchPostVo(
        Long memberId,
        Long postId,
        String title,
        String content,
        String thumbnail,
        String categoryType,
        String lastModifiedDate
) implements MemberIdField {

    @Override
    public Long getMemberId() {
        return this.memberId;
    }
}
