package org.bootstrap.apicomposer.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.exception.custom.UnAuthenticationException;
import org.bootstrap.apicomposer.global.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
@EnableWebFluxSecurity
@Slf4j
@RequiredArgsConstructor
public class WebFluxSecurityConfig {

    private final JwtProvider jwtProvider;
    public static final String USER_ID_KEY = "userId";

    private AuthenticationWebFilter authenticationWebFilter() {
        ReactiveAuthenticationManager authenticationManager = Mono::just;

        AuthenticationWebFilter authenticationWebFilter
                = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(serverAuthenticationConverter());
        return authenticationWebFilter;
    }

    private ServerAuthenticationConverter serverAuthenticationConverter(){
        return exchange -> {
            String token = jwtProvider.resolveToken(exchange.getRequest());
            try {
                if(!Objects.isNull(token) && jwtProvider.validateAccessToken(token)){
                    Authentication authentication = jwtProvider.getAuthentication(token);
                    exchange.getAttributes().put(USER_ID_KEY, authentication.getPrincipal());
                    return Mono.justOrEmpty(authentication);
                }
            } catch (UnAuthenticationException e) {
                log.error(e.getMessage(), e);
            }
            return Mono.empty();
        };
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/api/test").authenticated()
                                .anyExchange().permitAll()
                )
                .addFilterBefore(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(Customizer.withDefaults())
        ;
        return http.build();
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
                .roles("USER","ADMIN")
                .build();
        return new MapReactiveUserDetailsService(rob, admin);
    }
}
