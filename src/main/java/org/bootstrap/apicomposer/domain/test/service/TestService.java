package org.bootstrap.apicomposer.domain.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.helper.TestHelper;
import org.bootstrap.apicomposer.global.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestHelper testHelper;
    private final JwtProvider jwtProvider;

    public String webClientTest() {
        Mono<String> stringMono = testHelper.testGetMethod("https://jsonplaceholder.typicode.com/posts/1", String.class);
        stringMono.subscribe(value -> log.info("value: {}", value));

        return "test";
    }

    public String login(String email, String password) {
        if (!password.equals("8888"))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        String accessToken = jwtProvider.createAccessToken(1L);
        String refreshToken = jwtProvider.createRefreshToken();

        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        return accessToken;
    }
}
