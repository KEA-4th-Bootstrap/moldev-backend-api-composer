package org.bootstrap.apicomposer.domain.user.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.TrendUserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.BanDaysListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.TrendingMembersListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
                UriComponentsBuilder.fromHttpUrl(MEMBER_SERVICE_URL + "/api/member/info")
                        .queryParam("ids", userIds)
                        .toUriString(),
                headers,
                UserDetailListResponseDto.class);
    }

    public Mono<ResponseEntity<UserProfileVo>> getUserProfileVo(String moldevId, HttpHeaders headers) {
        return webClientUtil.api(MEMBER_SERVICE_URL + "/api/member/" + moldevId + "/profile",
                headers,
                UserProfileVo.class);
    }

    public Mono<ResponseEntity<TrendUserDetailListResponseDto>> getUserProfileVoByMoldevIds(List<String> moldevIds, HttpHeaders headers) {
        return webClientUtil.api(
                UriComponentsBuilder.fromHttpUrl(MEMBER_SERVICE_URL + "/api/member/moldev_info")
                        .queryParam("ids", moldevIds)
                        .toUriString(),
                headers,
                TrendUserDetailListResponseDto.class);
    }

    public Mono<ResponseEntity<TrendingMembersListResponseDto>> getTrendIslands(HttpHeaders headers) {
        return webClientUtil.api(MEMBER_SERVICE_URL + "/api/member/trend",
                headers,
                TrendingMembersListResponseDto.class);
    }

    public Mono<ResponseEntity<Void>> banUser(Long reportId, HttpHeaders headers, Object requestBody) {
        return webClientUtil.getApiWithBody(MEMBER_SERVICE_URL + "/api/member/ban/" + reportId,
                HttpMethod.POST,
                headers,
                requestBody,
                Void.class);
    }

    public Mono<ResponseEntity<BanDaysListResponseDto>> getUserBanDaysInfo(Object requestBody, HttpHeaders headers) {
        return webClientUtil.getApiWithBody(MEMBER_SERVICE_URL + "/api/member/banInfoOfReport",
                HttpMethod.GET,
                headers,
                requestBody,
                BanDaysListResponseDto.class);
    }
}
