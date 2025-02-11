package org.bootstrap.apicomposer.global.jwt;

import io.jsonwebtoken.*;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.global.exception.custom.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-expiration-hours}")
    private long accessExpirationHours;;

    @Value("${jwt.refresh-expiration-hours}")
    private long refreshExpirationHours;

    public static final String USER_ROLE_KEY = "adminYn";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    // access token 발급 method
    public String createAccessToken(Long userId) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(String.valueOf(userId))  // JWT 토큰 제목
                .claim(USER_ROLE_KEY, "ROLE_USER")   // 클레임 설정
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(accessExpirationHours, ChronoUnit.HOURS)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    // refresh token 발급 method
    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    // 토큰 subject 꺼내기 (유저 id)
    public Long getTokenSubject(String token) {
        return Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }


    // 액세스 토큰 검증
    public Boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(accessToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new UnAuthenticationException(e.getMessage());
        }
    }

    // 리프레시 토큰 검증
    public Boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(refreshToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new UnAuthenticationException(e.getMessage());
        }
    }

    public Authentication getAuthentication(String accessToken) throws UnAuthenticationException{
        Claims claims = getClaimsFormToken(accessToken);

        if(claims.get(USER_ROLE_KEY) == null){
            throw new UnAuthenticationException("Token without permission information");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(USER_ROLE_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    private Claims getClaimsFormToken(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            throw new UnAuthenticationException(e.getMessage());
        }
    }

    public Long getExpDateFromToken(String token) {
        Claims claims = getClaimsFormToken(token);

        return Long.parseLong(claims.get("exp").toString());
    }

    // header 토큰을 가져오는 기능
    public String getToken(ServerHttpRequest request){
        return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    public String resolveToken(ServerHttpRequest request){
        String bearerToken = getToken(request);

        if(!StringUtils.isBlank(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7);
        }

        return null;
    }
}
