package org.bootstrap.apicomposer.domain.post.contoller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.service.PostService;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.global.common.SuccessResponse;
import org.bootstrap.apicomposer.global.webclient.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/compose/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/search")
    public Mono<ApiResponse<?>> getSearchPosts(@RequestParam final String text,
                                               ServerHttpRequest request) {
        return postService.getSearchPosts(text, request);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> getPostInfo(@PathVariable("id") final Long postId,
                                                                @RequestParam final Long postWriterId,
                                                                ServerHttpRequest request) {
        return postService.getPostInfo(postId, postWriterId, request);
    }

    @GetMapping("/{moldevId}/category")
    public Mono<ApiResponse<?>> getCategoryPost(@PathVariable final String moldevId,
                                                                    @RequestParam final CategoryType type,
                                                                    ServerHttpRequest request) {
        return postService.getCategoryPost(moldevId, type, request);
    }
}
