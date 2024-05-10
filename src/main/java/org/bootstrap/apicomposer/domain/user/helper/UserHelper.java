package org.bootstrap.apicomposer.domain.user.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.vo.SearchPostVo;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;
import org.bootstrap.apicomposer.domain.user.vo.SearchUserVo;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserHelper {
    private final WebClientUtil webClientUtil;

    public Mono<UserDetailResponseDto> getSearchUserResult(List<Long> userIds, HttpHeaders headers) {
        return webClientUtil.api("http://localhost:8081/api/test", headers, UserDetailResponseDto.class);
    }
}
