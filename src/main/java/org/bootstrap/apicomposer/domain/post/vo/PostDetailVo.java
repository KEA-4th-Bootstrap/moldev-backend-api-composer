package org.bootstrap.apicomposer.domain.post.vo;

import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.global.utils.MemberIdField;
import org.bootstrap.apicomposer.global.utils.MoldevIdField;

import java.time.LocalDateTime;

@Builder
public record PostDetailVo(
        Long id,
        String moldevId,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        Integer viewCount,
        String lastModifiedDate
) implements MoldevIdField {
    @Override
    public String getMoldevId() {
        return this.moldevId;
    }
}
