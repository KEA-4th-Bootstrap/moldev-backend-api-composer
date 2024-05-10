package org.bootstrap.apicomposer.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailResponseDto;
import org.bootstrap.apicomposer.domain.post.dto.response.SearchPostsResponseDto;
import org.bootstrap.apicomposer.domain.post.helper.PostHelper;
import org.bootstrap.apicomposer.domain.post.vo.SearchPostVo;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostHelper postHelper;
    private final UserHelper userHelper;

    public Mono<SearchPostsResponseDto> getSearchPosts(String text, ServerHttpRequest request) {
        Mono<PostDetailResponseDto> searchPostVoMono = postHelper.getSearchPostResult(text, request.getHeaders());
        return searchPostVoMono.flatMap(result -> {
            List<Long> requestMembers = result.postList().stream()
                    .map(SearchPostVo::memberId)
                    .collect(Collectors.toList());
            Mono<UserDetailResponseDto> searchUserVoMono = userHelper.getSearchUserResult(requestMembers, request.getHeaders());
            return searchUserVoMono.map(nextResult -> SearchPostsResponseDto.of(result, nextResult));
        });
    }
}
