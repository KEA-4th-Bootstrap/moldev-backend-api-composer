package org.bootstrap.apicomposer.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ReplyReportDetailResponseDto(
        String replyId,
        String title,
        String replyContent
) {
    public static ReplyReportDetailResponseDto of(String replyId, String title, String replyContent) {
        return ReplyReportDetailResponseDto.builder()
                .replyId(replyId)
                .title(title)
                .replyContent(replyContent)
                .build();
    }
}
