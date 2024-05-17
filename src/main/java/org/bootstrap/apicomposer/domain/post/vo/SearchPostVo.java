package org.bootstrap.apicomposer.domain.post.vo;

import java.util.List;
import java.util.stream.Collectors;

public record SearchPostVo(
        Long memberId,
        Long postId,
        String title,
        String content,
        String thumbnail,
        String categoryType,
        String lastModifiedDate
) {
    public static List<Long> getRequestMembers(List<SearchPostVo> postList) {
        return postList.stream()
                .map(SearchPostVo::memberId)
                .collect(Collectors.toList());
    }
}
