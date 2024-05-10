package org.bootstrap.apicomposer.domain.test.dto.res;

import lombok.Builder;

@Builder
public record PostResponseDto(
        Long id,
        Long memberId,
        String title,
        String content,
        String thumbnail,
        String category
) {

    public static PostResponseDto of(Long id, Long memberId, String title, String content, String thumbnail, String category) {
        return PostResponseDto.builder()
                .id(id)
                .memberId(memberId)
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .category(category)
                .build();
    }
}
