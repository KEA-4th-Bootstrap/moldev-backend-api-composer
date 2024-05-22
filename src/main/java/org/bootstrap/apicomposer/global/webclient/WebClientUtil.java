package org.bootstrap.apicomposer.global.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.error.MsaExceptionUtil;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    private WebClient.RequestBodySpec baseAPI(String uri, HttpMethod httpMethod, HttpHeaders headers) {
        return webClientConfig.webClient()
                .method(httpMethod)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers));
    }

    private static <T> Mono<ResponseEntity<T>> apiRetrieve(WebClient.RequestBodySpec request, Class<T> responseClass) {
        return request.retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        WebClientUtil::handleErrorResponse
                )
                .toEntity(responseClass);
    }

    private static Mono<byte[]> apiRetrieve(WebClient.RequestBodySpec request, HttpHeaders headers) {
        return request.retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        WebClientUtil::handleErrorResponse
                )
                .bodyToMono(byte[].class);
    }

    private static Mono<byte[]> apiRetrieve(WebClient.RequestHeadersSpec<?> requestHeadersSpec, HttpHeaders headers) {
        return requestHeadersSpec.retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        WebClientUtil::handleErrorResponse
                )
                .bodyToMono(byte[].class);
    }

    public <T> Mono<ResponseEntity<T>> api(String uri, HttpHeaders headers, Class<T> responseClass) {
        WebClient.RequestBodySpec requestBodySpec = baseAPI(uri, HttpMethod.GET, headers);
        return apiRetrieve(requestBodySpec, responseClass);
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
        return apiRetrieve(baseAPI(uri, httpMethod, headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts)), headers);
    }

    private static Mono<Throwable> handleErrorResponse(ClientResponse response) {
        throw MsaExceptionUtil.Exception(response.statusCode());
    }
}
