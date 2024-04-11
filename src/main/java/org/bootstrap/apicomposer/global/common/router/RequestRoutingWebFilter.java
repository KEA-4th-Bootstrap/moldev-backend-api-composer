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

        if (HttpMethod.GET.equals(requestMethod)) {
            return chain.filter(exchange);
        } else {
            String baseUrl = "http://localhost:8080";
            String requestPath = requestUri.getPath();
            if (requestPath.contains("test1")) {
                baseUrl = "http://localhost:8081";
            } else if (requestPath.contains("test2")) {
                baseUrl = "http://localhost:8082";
            }

            URI newUri = URI.create(baseUrl + requestPath + (requestUri.getQuery() != null ? "?" + requestUri.getQuery() : ""));

            return webClientUtil.api(newUri.toString(), byte[].class, requestMethod, request.getBody(), request.getHeaders())
                    .flatMap(body -> {
                        log.info("response: {}", body);
                        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body)));
                    });
        }
    }
}
