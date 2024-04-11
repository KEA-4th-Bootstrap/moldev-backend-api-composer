package org.bootstrap.apicomposer.domain.test.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TestHelper {

    private final WebClientUtil webClientUtil;

    public <T> Mono<T> testGetMethod(String url, Class<T> responseType) {
        return webClientUtil.get(url, responseType);
    }
}
