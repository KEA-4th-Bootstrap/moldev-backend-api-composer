package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.Builder;
import org.bootstrap.apicomposer.domain.post.vo.SearchPostVo;
import org.bootstrap.apicomposer.global.common.PageInfo;

import java.util.List;

@Builder
public record PostDetailResponseDto(
        List<SearchPostVo> postList,
        PageInfo pageInfo
) {
}
