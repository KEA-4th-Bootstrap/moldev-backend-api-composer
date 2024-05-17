package org.bootstrap.apicomposer.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentDetailListResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentTotalResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.ReplyDetailListResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.ReplyTotalResponseDto;
import org.bootstrap.apicomposer.domain.reply.helper.ReplyHelper;
import org.bootstrap.apicomposer.domain.reply.vo.CommentReplyVo;
import org.bootstrap.apicomposer.domain.reply.vo.PostCommentVo;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.bootstrap.apicomposer.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyHelper replyHelper;
    private final UserHelper userHelper;

    public Mono<ResponseEntity<SuccessResponse<?>>> getCommentList(Long postId, ServerHttpRequest request) {
        Mono<ResponseEntity<CommentDetailListResponseDto>> commentListMono = replyHelper.getPostCommentResult(postId, request.getHeaders());
        return commentListMono.flatMap(result -> {
            List<Long> requestMembers = PostCommentVo.getRequestMemberId(result.getBody().commentList());
            Mono<ResponseEntity<UserDetailListResponseDto>> userVoMono = userHelper.getSearchUserResult(requestMembers, request.getHeaders());
            return userVoMono.map(nextResult -> {
                CommentTotalResponseDto responseDto = CommentTotalResponseDto.of(result.getBody(), nextResult.getBody());
                return SuccessResponse.ok(responseDto);
            });
        });
    }

    public Mono<ResponseEntity<SuccessResponse<?>>> getReplyList(String parentsId, ServerHttpRequest request) {
        Mono<ResponseEntity<ReplyDetailListResponseDto>> replyListMono = replyHelper.getCommentReplyResult(parentsId, request.getHeaders());
        return replyListMono.flatMap(result -> {
            List<Long> requestMembers = CommentReplyVo.getRequestMemberId(result.getBody().replyList());
            Mono<ResponseEntity<UserDetailListResponseDto>> userVoMono = userHelper.getSearchUserResult(requestMembers, request.getHeaders());
            return userVoMono.map(nextResult -> {
                ReplyTotalResponseDto responseDto = ReplyTotalResponseDto.of(result.getBody(), nextResult.getBody());
                return SuccessResponse.ok(responseDto);
            });
        });
    }
}
