package org.bootstrap.apicomposer.global.common.auth;

import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasUserIdAnnotation = parameter.hasParameterAnnotation(UserId.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasUserIdAnnotation && hasLongType;
    }

    @Override
    public Mono<Object> resolveArgument(@Nonnull MethodParameter parameter, @Nonnull BindingContext bindingContext, @Nonnull ServerWebExchange exchange) {
        String userId = exchange.getAttribute("userId");
        return userId != null ? Mono.just(Long.valueOf(userId)) : Mono.empty();
    }
}
