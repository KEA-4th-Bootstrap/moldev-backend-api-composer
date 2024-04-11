package org.bootstrap.apicomposer.global.common.router;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
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

        if (HttpMethod.GET.equals(requestMethod)) {
            return chain.filter(exchange);
        }

        String host = requestPath.split("/")[2] + ":8081";
        String newUri = createUri(host, requestPath, requestUri.getQuery());

        return webClientUtil.api(newUri, byte[].class, requestMethod, request.getBody(), request.getHeaders())
                .flatMap(body -> exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body))));
    }

    private String createUri(String host, String path, String query) {
        return "http://" + host + path + (query != null ? "?" + query : "");
    }
}
