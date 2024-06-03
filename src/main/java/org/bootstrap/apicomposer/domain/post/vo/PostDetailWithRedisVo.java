package org.bootstrap.apicomposer.domain.post.vo;

import lombok.Builder;
import lombok.Getter;
import org.bootstrap.apicomposer.global.utils.MemberIdField;
import org.bootstrap.apicomposer.global.utils.MoldevIdField;

@Builder
public record PostDetailWithRedisVo(
        PostDetailVo postInfo,
        Integer redisViewCount
) implements MoldevIdField {
    @Override
    public String getMoldevId() {
        return this.postInfo().moldevId();
    }
}
