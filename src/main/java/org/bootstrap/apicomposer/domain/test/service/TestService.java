package org.bootstrap.apicomposer.domain.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.dto.req.PostRequestDto;
import org.bootstrap.apicomposer.domain.test.dto.res.TokenResponse;
import org.bootstrap.apicomposer.domain.test.helper.TestHelper;
import org.bootstrap.apicomposer.global.jwt.JwtProvider;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestHelper testHelper;
    private final JwtProvider jwtProvider;
    private final WebClientUtil webClientUtil;

    public Mono<byte[]> webClientTest(HttpHeaders request) {
        Mono<byte[]> stringMono = testHelper.testGetMethod("https://www.google.com", request);
        stringMono.subscribe(value -> log.info("value: {}", value));

        return stringMono;
    }

    public Mono<byte[]> webClientTestForPost(HttpHeaders request, Long id) {
        return testHelper.testGetMethod("http://post-service.default.svc.cluster.local:80/api/post/" + id, request)
                .doOnNext(value -> log.info("value: {}", value))
                .onErrorResume(e -> Mono.just(("Error occurred: " + e.getMessage()).getBytes()));
    }

    public Mono<byte[]> testPost(PostRequestDto request, HttpHeaders headers) {
        return webClientUtil.api("http://localhost:8081/api/test", HttpMethod.POST, request, headers);
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
