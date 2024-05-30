package org.bootstrap.apicomposer.global.common.router;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.bootstrap.apicomposer.global.common.response.SuccessCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

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
        log.info("Request path: " + requestPath);
        if (!request.getQueryParams().isEmpty()) {
            log.info("path with params: {}", requestUri.getPath() + "?" + requestUri.getQuery());
        }

        if (requestPath.contains("compose")) {
            log.info("Composition request: proceed to controller");
            return chain.filter(exchange);
        }

        if (requestPath.contains("login")) {
            log.info("Login request");
            return webClientUtil.setCookieApi(exchange, requestPath);
        } else if (requestPath.contains("reissue")) {
            log.info("Reissue request");
            return webClientUtil.setCookieApi(exchange, requestPath);
        }

        String userId = exchange.getAttribute(HttpHeaders.AUTHORIZATION);
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(request.getHeaders());
        headers.set("Authorization", userId);

        MediaType contentType = headers.getContentType();
        String newUri = createUrl(requestPath, request);
        log.info("Routing URL: {}", newUri);

        return handleRequest(requestMethod, request, contentType, newUri, headers, exchange);
    }

    private Mono<Void> handleRequest(HttpMethod method, ServerHttpRequest request, MediaType contentType, String url, HttpHeaders headers, ServerWebExchange exchange) {
        if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
            return webClientUtil.api(url, method, request.getBody(), headers)
                    .flatMap(data -> writeResponse(exchange, new String(data, StandardCharsets.UTF_8)));
        } else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
            return exchange.getMultipartData()
                    .flatMap(parts -> webClientUtil.api(method, url, parts, headers))
                    .flatMap(data -> writeResponse(exchange, new String(data, StandardCharsets.UTF_8)));
        } else if (method.equals(HttpMethod.GET)) {
            return webClientUtil.api(url, HttpMethod.GET, headers)
                    .flatMap(data -> writeResponse(exchange, new String(data, StandardCharsets.UTF_8)));
        } else {
            return Mono.error(new UnsupportedOperationException("Unsupported content type: " + contentType));
        }
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, String data) {
        Gson gson = new Gson();
        JsonObject dataJson = gson.fromJson(data, JsonObject.class);
        ApiResponse<?> finalResponse = ApiResponse.of(SuccessCode.SUCCESS, dataJson);
        String json = gson.toJson(finalResponse);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(json.getBytes())));
    }

    private String createUrl(String path, ServerHttpRequest request) {
        String domain = path.split("/")[2];
        log.info("target: {} service", domain);
        return UriComponentsBuilder.fromHttpUrl(String.format("http://%s-service.backend.svc:80%s", domain, path))
                        .replaceQueryParams(request.getQueryParams())
                        .toUriString();
    }
}
