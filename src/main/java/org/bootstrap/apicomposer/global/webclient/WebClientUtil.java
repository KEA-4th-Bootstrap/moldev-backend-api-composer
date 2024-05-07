package org.bootstrap.apicomposer.global.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClientConfig webClientConfig;
    
    private WebClient.RequestBodySpec baseAPI(String uri, HttpMethod httpMethod, HttpHeaders headers){
        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers));
    };

    private static Mono<byte[]> apiRetrieve(WebClient.RequestBodySpec request, HttpHeaders headers){
        return request.retrieve()
                .bodyToMono(byte[].class)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    log.error("""
                                    Error calling external API: {}
                                    RequestHeaders: {}
                                    """, e.getMessage(), headers);

                    return Mono.empty();
                });
    }

    private static Mono<byte[]> apiRetrieve(WebClient.RequestHeadersSpec<?> requestHeadersSpec, HttpHeaders headers){
        return requestHeadersSpec.retrieve()
                .bodyToMono(byte[].class)
                .onErrorResume(e -> {
                    // 에러 처리 로직
                    log.error("""
                                    Error calling external API: {}
                                    RequestHeaders: {}
                                    """, e.getMessage(), headers);

                    return Mono.empty();
                });
    }

    public Mono<byte[]> api(String uri, HttpMethod httpMethod, HttpHeaders headers) {
        return apiRetrieve(baseAPI(uri, httpMethod, headers), headers);
    }

    public Mono<byte[]> api(String uri, HttpMethod httpMethod, Publisher<DataBuffer> requestBody, HttpHeaders headers) {
        return apiRetrieve(baseAPI(uri, httpMethod, headers).body(BodyInserters.fromDataBuffers(requestBody)), headers);
    }

    public Mono<byte[]> api(String uri, HttpMethod httpMethod, Object requestBody, HttpHeaders headers) {
        return apiRetrieve(baseAPI(uri, httpMethod, headers).bodyValue(requestBody), headers);
    }

    public Mono<byte[]> api(HttpMethod httpMethod, String uri, MultiValueMap<String, Part> parts, HttpHeaders headers) {
        // WebClient를 사용하여 멀티파트 요청을 설정
        return apiRetrieve(baseAPI(uri, httpMethod, headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts)), headers);
    }
}
