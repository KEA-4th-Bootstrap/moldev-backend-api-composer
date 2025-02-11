package org.bootstrap.apicomposer.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.common.router.RequestRoutingWebFilter;
import org.bootstrap.apicomposer.global.exception.custom.UnAuthenticationException;
import org.bootstrap.apicomposer.global.jwt.JwtProvider;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
@EnableWebFluxSecurity
@Slf4j
@RequiredArgsConstructor
public class WebFluxSecurityConfig {
    private final CorsConfig corsConfig;
    private final JwtProvider jwtProvider;
    private final WebClientUtil webClientUtil;

    private static final String[] whiteList = {
            "/api/auth/**",
            "/api/post/mission-control",
            "/api/post/*/category",
            "/api/post/*/category/list",
            "/api/post/mission-control",
            "/api/post/trend",
            "/api/member/password",
            "/api/member/*/profile",
            "/api/member/trend",
            "/api/member/search",
            "/api/compose/**",
            "/api/search/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(whiteList).permitAll()
                        .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .addFilterAt(corsConfig.corsWebFilter(), SecurityWebFiltersOrder.CORS)
                .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(new RequestRoutingWebFilter(webClientUtil), SecurityWebFiltersOrder.LAST)
                .exceptionHandling(exceptionHandlingSpec ->
                        exceptionHandlingSpec
                                .authenticationEntryPoint((exchange, ex) -> {
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                })
                )
                .cors(ServerHttpSecurity.CorsSpec::disable);
        return http.build();
    }

    private AuthenticationWebFilter authenticationWebFilter() {
        ReactiveAuthenticationManager authenticationManager = Mono::just;
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(serverAuthenticationConverter());
        return authenticationWebFilter;
    }

    private ServerAuthenticationConverter serverAuthenticationConverter() {
        return exchange -> {
            String token = jwtProvider.resolveToken(exchange.getRequest());
            try {
                if (Objects.isNull(token) || !jwtProvider.validateAccessToken(token)) {
                    throw new UnAuthenticationException("Invalid Token");
                }
                Authentication authentication = jwtProvider.getAuthentication(token);
                exchange.getAttributes().put(HttpHeaders.AUTHORIZATION, authentication.getPrincipal());
                return Mono.justOrEmpty(authentication);
            } catch (UnAuthenticationException e) {
                log.error(e.getMessage());
            }
            return Mono.empty();
        };
    }
}
