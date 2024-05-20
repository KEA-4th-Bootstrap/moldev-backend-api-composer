package org.bootstrap.apicomposer.domain.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailListResponseDto;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailResponseDto;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.bootstrap.apicomposer.global.common.Constants.POST_SERVICE_URL;
import static org.bootstrap.apicomposer.global.common.Constants.SEARCH_SERVICE_URL;

@RequiredArgsConstructor
@Component
public class PostHelper {
    private final WebClientUtil webClientUtil;

    public Mono<ResponseEntity<PostDetailListResponseDto>> getSearchPostResult(String text, HttpHeaders headers) {
        return webClientUtil.api(
                UriComponentsBuilder.fromHttpUrl(SEARCH_SERVICE_URL + "/api/search/posts")
                        .queryParam("title", text)
                        .toUriString(),
                headers, PostDetailListResponseDto.class);
    }

    public Mono<ResponseEntity<PostDetailResponseDto>> getPostDetailInfoResult(Long postId, HttpHeaders headers) {
        return webClientUtil.api(POST_SERVICE_URL + "/api/post/" + postId, headers, PostDetailResponseDto.class);
    }

    public Mono<ResponseEntity<CategoryPostVo>> getCategoryPost(String moldevId, CategoryType type, HttpHeaders headers) {
        return webClientUtil.api(
                UriComponentsBuilder.fromHttpUrl(POST_SERVICE_URL + "/api/compose/post/" + moldevId + "/category")
                        .queryParam("type", type)
                        .toUriString(),
                headers,
                CategoryPostVo.class
        );
    }
}
