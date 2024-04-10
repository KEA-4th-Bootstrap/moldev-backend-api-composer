package org.bootstrap.apicomposer.global.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> Mono<T> get(String url, Class<T> responseType) {
        return webClientConfig.webClient()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> post(String url, Object requestBody, Class<T> responseType) {
        return webClientConfig.webClient()
                .post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> patch(String url, Object requestBody, Class<T> responseType) {
        return webClientConfig.webClient()
                .patch()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> delete(String url, Class<T> responseType) {
        return webClientConfig.webClient()
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }
}
