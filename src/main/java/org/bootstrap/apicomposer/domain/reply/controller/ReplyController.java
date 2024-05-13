package org.bootstrap.apicomposer.domain.reply.controller;


import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentTotalResponseDto;
import org.bootstrap.apicomposer.domain.reply.dto.response.ReplyTotalResponseDto;
import org.bootstrap.apicomposer.domain.reply.service.ReplyService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/compose/reply")
@RestController
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 정보 불러오기
     * 댓글 서비스 댓글 정보 불러오고 -> 댓글 단 유저 정보 가져옴
     */
    @GetMapping("/{id}")
    public Mono<CommentTotalResponseDto> getCommentList(@PathVariable("id") final Long postId,
                                                        ServerHttpRequest request) {
        return replyService.getCommentList(postId, request);
    }

    /**
     * 답글 정보 불러오기
     * 답글 서비스 답글 정보 불러오고 -> 답글 단 유저 정보 가져옴
     */
    @GetMapping("")
    public Mono<ReplyTotalResponseDto> getCommentList(@RequestParam final String parentsId,
                                                      ServerHttpRequest request) {
        return replyService.getReplyList(parentsId, request);
    }
}
