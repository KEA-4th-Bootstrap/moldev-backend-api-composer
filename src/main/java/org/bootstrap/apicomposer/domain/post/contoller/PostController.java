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
                                               @RequestParam(required = false, defaultValue = "10") Integer size,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               ServerHttpRequest request) {
        return postService.getSearchPosts(title, size, page, request);
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

    @GetMapping("/trend-island")
    public Mono<ApiResponse<?>> getTrendIsland(ServerHttpRequest request) {
        return postService.getTrendIsland(request);
    }
}
