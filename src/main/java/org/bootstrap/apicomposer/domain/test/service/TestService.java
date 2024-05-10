package org.bootstrap.apicomposer.domain.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.dto.req.PostRequestDto;
import org.bootstrap.apicomposer.domain.test.dto.res.GetResponseDto;
import org.bootstrap.apicomposer.domain.test.dto.res.PostResponseDto;
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
import reactor.util.function.Tuple3;

import java.util.Arrays;
import java.util.concurrent.Flow;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestHelper testHelper;
    private final JwtProvider jwtProvider;
    private final WebClientUtil webClientUtil;

    public Mono<GetResponseDto> webClientTest(HttpHeaders request) {
        Mono<String> stringMono = testHelper.testGetMethod(request);
        Mono<String> stringMono2 = testHelper.testGetMethod(request);
        Mono<String> stringMono3 = testHelper.testGetMethod(request);

        return Mono.zip(stringMono, stringMono2, stringMono3).flatMap(tuple ->
            Mono.just(GetResponseDto.of(tuple.getT1(), tuple.getT2(), tuple.getT3()))
        );
    }

    public Mono<PostResponseDto> webClientTestForPost(HttpHeaders request, Long id) {
        return testHelper.testGetPostMethod("http://post-service.default.svc.cluster.local:80/api/post/" + id, request);
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
