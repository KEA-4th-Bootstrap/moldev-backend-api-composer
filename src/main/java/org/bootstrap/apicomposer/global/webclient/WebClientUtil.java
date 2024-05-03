package org.bootstrap.apicomposer.global.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> Mono<T> api(String uri, Class<T> responseType, HttpMethod httpMethod, HttpHeaders headers) {
        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    log.error("Error calling external API: {}", e.getMessage(), e);
                    return Mono.empty();
                });
    }

    public <T> Mono<T> api(String uri, Class<T> responseType, HttpMethod httpMethod, Object requestBody, HttpHeaders headers) {
        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(requestBody), requestBody.getClass())
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    log.error("Error calling external API: {}", e.getMessage(), e);
                    return Mono.empty();
                });
    }

    public Mono<byte[]> apiMultipart(HttpMethod httpMethod, String uri, MultiValueMap<String, Part> parts, HttpHeaders headers) {
        // WebClient를 사용하여 멀티파트 요청을 설정
        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve() // 서버로부터의 응답을 가져옴
                .bodyToMono(byte[].class)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    log.error("Error calling external API: {}", e.getMessage(), e);
                    return Mono.empty();
                });
    }
}
