package org.bootstrap.apicomposer.domain.report.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static org.bootstrap.apicomposer.global.common.Constants.REPORT_SERVICE_URL;

@RequiredArgsConstructor
@Component
public class ReportHelper {
    private final WebClientUtil webClientUtil;

    public Mono<byte[]> updateReportProcess(Long reportId, HttpHeaders headers) {
        return webClientUtil.api(REPORT_SERVICE_URL + "/api/report/" + reportId + "/processed",
                HttpMethod.PATCH,
                headers);
    }
}
