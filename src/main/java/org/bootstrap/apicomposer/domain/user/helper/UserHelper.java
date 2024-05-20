package org.bootstrap.apicomposer.domain.user.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.bootstrap.apicomposer.global.common.Constants.MEMBER_SERVICE_URL;

@RequiredArgsConstructor
@Component
public class UserHelper {
    private final WebClientUtil webClientUtil;

    public Mono<ResponseEntity<UserDetailListResponseDto>> getSearchUserResult(List<Long> userIds, HttpHeaders headers) {
        return webClientUtil.api(
                UriComponentsBuilder.fromHttpUrl(MEMBER_SERVICE_URL + "/api/members/info")
                        .queryParam("ids", userIds)
                        .toUriString(),
                headers,
                UserDetailListResponseDto.class);
    }

    public Mono<ResponseEntity<UserDetailResponseDto>> getUserDetailInfoResult(Long userId, HttpHeaders headers) {
        return webClientUtil.api(MEMBER_SERVICE_URL + "/api/members/" +userId,
                headers,
                UserDetailResponseDto.class);
    }

    public Mono<ResponseEntity<UserProfileVo>> getUserProfileVo(String moldevId, HttpHeaders headers) {
        return webClientUtil.api(MEMBER_SERVICE_URL + "/api/members/" + moldevId + "/profile",
                headers,
                UserProfileVo.class);
    }
}
