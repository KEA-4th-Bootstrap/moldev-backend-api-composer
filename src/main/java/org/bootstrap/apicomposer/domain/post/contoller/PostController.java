package org.bootstrap.apicomposer.domain.post.contoller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.SearchPostsResponseDto;
import org.bootstrap.apicomposer.domain.post.service.PostService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/compose/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/search")
    public Mono<SearchPostsResponseDto> getSearchPosts(@RequestParam final String text,
                                                       ServerHttpRequest request) {
        return postService.getSearchPosts(text, request);
    }
}
