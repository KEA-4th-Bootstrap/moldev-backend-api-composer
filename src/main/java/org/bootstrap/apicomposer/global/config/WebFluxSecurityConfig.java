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
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
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

    private final JwtProvider jwtProvider;
    private final WebClientUtil webClientUtil;

    private AuthenticationWebFilter authenticationWebFilter() {
        ReactiveAuthenticationManager authenticationManager = Mono::just;
        AuthenticationWebFilter authenticationWebFilter
                = new AuthenticationWebFilter(authenticationManager);
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

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
                .addFilterBefore(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(new RequestRoutingWebFilter(webClientUtil), SecurityWebFiltersOrder.AUTHORIZATION)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // 모든 출처를 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드를 허용
        configuration.addAllowedHeader("*"); // 모든 헤더를 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails rob = userBuilder.username("rob")
                .password("rob")
                .roles("USER")
                .build();
        UserDetails admin = userBuilder.username("admin")
                .password("admin")
                .roles("USER", "ADMIN")
                .build();
        return new MapReactiveUserDetailsService(rob, admin);
    }
}
