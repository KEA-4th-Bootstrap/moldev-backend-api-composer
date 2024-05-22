package org.bootstrap.apicomposer.domain.post.contoller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.service.PostService;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/compose/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/search")
    public Mono<ApiResponse<?>> getSearchPosts(@RequestParam final String title,
                                               ServerHttpRequest request) {
        return postService.getSearchPosts(title, request);
    }

    @GetMapping("/{moldevId}/{postId}")
    public Mono<ApiResponse<?>> getPostDetail(@PathVariable final String moldevId,
                                            @PathVariable final Long postId,
                                            ServerHttpRequest request) {
        return postService.getPostInfo(postId, moldevId, request);
    }

    @GetMapping("/{moldevId}/category")
    public Mono<ApiResponse<?>> getCategoryPost(@PathVariable final String moldevId,
                                                @RequestParam final CategoryType type,
                                                ServerHttpRequest request) {
        return postService.getCategoryPost(moldevId, type, request);
    }

    @GetMapping("/trend")
    public Mono<ApiResponse<?>> getTrendPost(ServerHttpRequest request) {
        return postService.getTrendPost(request);
    }
}
