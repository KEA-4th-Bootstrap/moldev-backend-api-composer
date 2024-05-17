package org.bootstrap.apicomposer.domain.reply.controller;


import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.reply.service.ReplyService;
import org.bootstrap.apicomposer.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/compose/reply")
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<SuccessResponse<?>>> getCommentList(@PathVariable("id") final Long postId,
                                                                   ServerHttpRequest request) {
        return replyService.getCommentList(postId, request);
    }

    @GetMapping("")
    public Mono<ResponseEntity<SuccessResponse<?>>> getCommentList(@RequestParam final String parentsId,
                                                      ServerHttpRequest request) {
        return replyService.getReplyList(parentsId, request);
    }
}
