package org.bootstrap.apicomposer.domain.reply.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailListResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentDetailListResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.ReplyDetailListResponseDto;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static org.bootstrap.apicomposer.global.common.Constants.REPLY_SERVICE_URL;

@RequiredArgsConstructor
@Component
public class ReplyHelper {

    private final WebClientUtil webClientUtil;

    public Mono<CommentDetailListResponseDto> getPostCommentResult(Long postId, HttpHeaders headers) {
        return webClientUtil.api(REPLY_SERVICE_URL+"/api/reply", headers, CommentDetailListResponseDto.class);
    }

    public Mono<ReplyDetailListResponseDto> getCommentReplyResult(String commentId, HttpHeaders headers) {
        return webClientUtil.api(REPLY_SERVICE_URL+"/api/reply", headers, ReplyDetailListResponseDto.class);
    }
}
