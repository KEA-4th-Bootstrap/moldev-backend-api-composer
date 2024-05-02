package org.bootstrap.apicomposer.domain.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.dto.res.TokenResponse;
import org.bootstrap.apicomposer.domain.test.helper.TestHelper;
import org.bootstrap.apicomposer.global.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestHelper testHelper;
    private final JwtProvider jwtProvider;

    public Mono<byte[]> webClientTest(HttpHeaders request) {
        Mono<byte[]> stringMono = testHelper.testGetMethod("https://www.google.com", byte[].class, request);
        stringMono.subscribe(value -> log.info("value: {}", value));

        return stringMono;
    }

    public Mono<byte[]> webClientTestForPost(HttpHeaders request, Long id) {
        return testHelper.testGetMethod("http://post-service.default.svc.cluster.local:80/api/post/" + id, byte[].class, request)
                .doOnNext(value -> log.info("value: {}", value))
                .onErrorResume(e -> Mono.just(("Error occurred: " + e.getMessage()).getBytes()));
    }

    public TokenResponse login(String email, String password) {
        if (!password.equals("8888"))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        String accessToken = jwtProvider.createAccessToken(1L);
        String refreshToken = jwtProvider.createRefreshToken();

        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
