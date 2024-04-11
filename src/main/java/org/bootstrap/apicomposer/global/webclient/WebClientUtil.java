package org.bootstrap.apicomposer.global.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> Mono<T> get(String url, Class<T> responseType) {
        return webClientConfig.webClient()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> api(String uri, Class<T> responseType, HttpMethod httpMethod, HttpHeaders headers) {
        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    // 예를 들어, 에러 로그를 출력하고, 에러에 대한 정보를 담은 Mono를 반환
                    log.error("Error calling external API: {}", e.getMessage(), e);
                    // 적절한 에러 처리를 위해 Mono.empty() 반환 또는 에러 정보를 담은 Mono 반환
                    return Mono.empty();
                });
    }

    public <T> Mono<T> api(String uri, Class<T> responseType, HttpMethod httpMethod, Object requestBody, HttpHeaders headers) {
        log.info("httpMethod: {}", httpMethod);
        log.info("uri: {}", uri);
        log.info("requestBody: {}", requestBody);
        log.info("headers: {}", headers);

        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(requestBody), requestBody.getClass())
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    // 예를 들어, 에러 로그를 출력하고, 에러에 대한 정보를 담은 Mono를 반환
                    log.error("Error calling external API: {}", e.getMessage(), e);
                    // 적절한 에러 처리를 위해 Mono.empty() 반환 또는 에러 정보를 담은 Mono 반환
                    return Mono.empty();
                });
    }
}
