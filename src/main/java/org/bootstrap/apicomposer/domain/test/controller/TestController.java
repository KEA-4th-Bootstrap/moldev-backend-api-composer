package org.bootstrap.apicomposer.domain.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.service.TestService;
import org.bootstrap.apicomposer.global.common.auth.UserId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    private final TestService testService;

    @GetMapping
    public Mono<String> test(
            @UserId Long userId
            ) {
        log.info("userId: {}", userId);
        return Mono.just(testService.webClientTest());
    }

    @GetMapping("/free")
    public Mono<String> noAuthTest() {
        return Mono.just(testService.webClientTest());
    }

    @GetMapping("/login")
    public Mono<String> testLogin(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return Mono.just(testService.login(email, password));
    }
}
