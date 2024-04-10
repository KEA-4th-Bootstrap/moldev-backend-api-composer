package org.bootstrap.apicomposer.domain.test.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.test.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @GetMapping
    public Mono<String> test() {
        return Mono.just(testService.webClientTest());
    }
}
