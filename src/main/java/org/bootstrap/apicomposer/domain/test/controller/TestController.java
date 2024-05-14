package org.bootstrap.apicomposer.domain.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.dto.req.PostRequestDto;
import org.bootstrap.apicomposer.domain.test.dto.res.GetResponseDto;
import org.bootstrap.apicomposer.domain.test.dto.res.TokenResponse;
import org.bootstrap.apicomposer.domain.test.service.TestService;
import org.bootstrap.apicomposer.global.common.auth.UserId;
import org.bootstrap.apicomposer.global.webclient.response.ApiResponse;
import org.bootstrap.apicomposer.global.webclient.response.SuccessCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    private final TestService testService;

    @GetMapping("/compose")
    public Mono<ApiResponse<?>> test(
            @UserId Long userId,
            ServerHttpRequest request
            ) {
        log.info("userId: {}", userId);
        return testService.webClientTest(request.getHeaders());
    }

    @GetMapping("/free")
    public Mono<ApiResponse<?>> noAuthTest(
            ServerHttpRequest request
    ) {
        return testService.webClientTest(request.getHeaders());
    }

    @GetMapping("/login")
    public Mono<TokenResponse> testLogin(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return Mono.just(testService.login(email, password));
    }

    @PostMapping("/post")
    public Mono<byte[]> postTest(
            @RequestBody PostRequestDto requestDto,
            ServerHttpRequest request
    ) {
        return testService.testPost(requestDto, request.getHeaders());
    }
}
