package org.bootstrap.apicomposer.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.report.helper.ReportHelper;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.bootstrap.apicomposer.global.common.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserHelper userHelper;
    private final ReportHelper reportHelper;

    public Mono<ApiResponse<?>> banUserByReport(Long reportId, Object requestBody, ServerHttpRequest request) {
        Mono<ResponseEntity<Void>> responseEntityMono = userHelper.banUser(reportId, request.getHeaders(), requestBody);
        Mono<byte[]> mono = reportHelper.updateReportProcess(reportId, request.getHeaders());
        return Mono.zip(responseEntityMono, mono).flatMap(result -> Mono.just(ApiResponse.of(SuccessCode.SUCCESS, null)));
    }

}
