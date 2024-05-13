package org.bootstrap.apicomposer.domain.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailListResponseDto;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailResponseDto;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static org.bootstrap.apicomposer.global.common.Constants.POST_SERVICE_URL;

@RequiredArgsConstructor
@Component
public class PostHelper {
    private final WebClientUtil webClientUtil;

    public Mono<PostDetailListResponseDto> getSearchPostResult(String text, HttpHeaders headers) {
        return webClientUtil.api(POST_SERVICE_URL + "/api/test", headers, PostDetailListResponseDto.class);
    }

    public Mono<PostDetailResponseDto> getPostDetailInfoResult(Long postId, HttpHeaders headers) {
        return webClientUtil.api(POST_SERVICE_URL + "/api/post/" + postId, headers, PostDetailResponseDto.class);
    }

    public Mono<CategoryPostVo> getCategoryPost(String moldevId, HttpHeaders headers) {
        return webClientUtil.api(POST_SERVICE_URL + "/api/compose/" + moldevId + "/post", headers, CategoryPostVo.class);
    }
}
