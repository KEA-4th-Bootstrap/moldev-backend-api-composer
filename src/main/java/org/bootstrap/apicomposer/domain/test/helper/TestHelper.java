package org.bootstrap.apicomposer.domain.test.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.test.dto.res.PostResponseDto;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TestHelper {

    private final WebClientUtil webClientUtil;

    public Mono<String> testGetMethod(HttpHeaders headers) {
        // 해당 코드는 다른 환경에서 돌아가지 않습니다.
        return webClientUtil.api("http://localhost:8081/api/test", headers, String.class);
    }

    public Mono<PostResponseDto> testGetPostMethod(String url, HttpHeaders headers) {
        // 해당 코드는 post 서비스가 배포된 쿠버네티스 클러스터 내부에서만 동작합니다.
        return webClientUtil.api(url, headers, PostResponseDto.class);
    }
}
