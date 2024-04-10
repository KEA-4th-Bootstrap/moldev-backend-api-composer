package org.bootstrap.apicomposer.domain.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.test.helper.TestHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestHelper testHelper;

    public String webClientTest() {
        Mono<String> stringMono = testHelper.testGetMethod("https://jsonplaceholder.typicode.com/posts/1", String.class);
        stringMono.subscribe(value -> log.info("value: {}", value));

        return "test";
    }
}
