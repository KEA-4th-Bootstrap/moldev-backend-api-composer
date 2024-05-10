package org.bootstrap.apicomposer.domain.test.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.test.dto.res.PostResponseDto;
import org.bootstrap.apicomposer.domain.test.service.TestService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/post")
@RequiredArgsConstructor
@RestController
public class TestPostController {

    private final TestService testService;

    @GetMapping("/{id}")
    public Mono<PostResponseDto> postDomainTest(
            ServerHttpRequest request,
            @PathVariable Long id
    ) {
        return testService.webClientTestForPost(request.getHeaders(), id);
    }
}
