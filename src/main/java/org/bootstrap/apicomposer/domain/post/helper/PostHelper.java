package org.bootstrap.apicomposer.domain.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailResponseDto;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class PostHelper {
    private final WebClientUtil webClientUtil;

    public Mono<PostDetailResponseDto> getSearchPostResult(String text, HttpHeaders headers) {
        return webClientUtil.api("http://localhost:8081/api/test", headers, PostDetailResponseDto.class);
    }
}
