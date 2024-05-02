package org.bootstrap.apicomposer.global.common.router;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
public class RequestRoutingWebFilter implements WebFilter {

    private final WebClientUtil webClientUtil;
    @Override
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod requestMethod = request.getMethod();
        URI requestUri = request.getURI();
        String requestPath = requestUri.getPath();
        String userId = exchange.getAttribute(HttpHeaders.AUTHORIZATION);
        HttpHeaders headers = request.getHeaders();

        if (userId != null) {
            headers.add(HttpHeaders.AUTHORIZATION, userId);
        }

        if (HttpMethod.GET.equals(requestMethod)) {
            return chain.filter(exchange);
        }

        MediaType contentType = headers.getContentType();
        String newUri = createUri(requestPath);
        log.info("routing to: {}", newUri);

        if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
            return webClientUtil.api(newUri, byte[].class, requestMethod, request.getBody(), headers)
                    .flatMap(body -> exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body))));
        } else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
            // Multipart 요청 처리
            return exchange.getMultipartData()
                    .flatMap(parts -> webClientUtil.apiMultipart(requestMethod, newUri, parts, headers))
                    .flatMap(body -> exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body))));
        } else {
            // 지원하지 않는 컨텐트 타입에 대한 처리
            return Mono.error(new UnsupportedOperationException("Unsupported content type: " + contentType));
        }
    }

    private String createUri(String path) {
        String host = path.split("/")[2];
        return "http://" + host + "-service.default.svc.cluster.local:80" + path;
    }
}
