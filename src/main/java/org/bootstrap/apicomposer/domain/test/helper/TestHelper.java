package org.bootstrap.apicomposer.domain.test.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TestHelper {

    private final WebClientUtil webClientUtil;

    public Mono<byte[]> testGetMethod(String url, HttpHeaders headers) {
        return webClientUtil.api(url, HttpMethod.GET, headers);
    }
}
