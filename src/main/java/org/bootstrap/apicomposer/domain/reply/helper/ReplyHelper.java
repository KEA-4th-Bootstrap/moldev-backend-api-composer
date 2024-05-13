package org.bootstrap.apicomposer.domain.reply.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentCountResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentDetailListResponseDto;
//import org.bootstrap.apicomposer.domain.reply.dto.response.ReplyDetailListResponseDto;
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

    // 게시글 댓글 정보 가져오기
    public Mono<CommentDetailListResponseDto> getPostCommentResult(Long postId, HttpHeaders headers) {
        return webClientUtil.api(REPLY_SERVICE_URL + "/api/reply/"+postId, headers, CommentDetailListResponseDto.class);
    }

    // 답글 정보 가져오기
    public Mono<ReplyDetailListResponseDto> getCommentReplyResult(String commentId, HttpHeaders headers) {
        return webClientUtil.api(REPLY_SERVICE_URL + "/api/reply", headers, ReplyDetailListResponseDto.class);
    }

    // 게시글 댓글 개수 가져오기
    public Mono<CommentCountResponseDto> getPostCommentCount(Long postId, HttpHeaders headers) {
        return webClientUtil.api(REPLY_SERVICE_URL + "/api/reply/count/"+postId, headers, CommentCountResponseDto.class);
    }
}
