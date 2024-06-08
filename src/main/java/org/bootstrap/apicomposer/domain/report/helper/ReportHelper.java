package org.bootstrap.apicomposer.domain.report.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.report.dto.ReportResponseDto;
import org.bootstrap.apicomposer.domain.report.entity.ReportType;
import org.bootstrap.apicomposer.global.webclient.WebClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
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

    public Mono<ResponseEntity<ReportResponseDto>> getReportProcessed(ReportType reportType, String search, Integer size, Integer page, HttpHeaders headers) {
        return webClientUtil.api(
                UriComponentsBuilder.fromHttpUrl(REPORT_SERVICE_URL + "/api/report/processed")
                        .queryParam("type", reportType)
                        .queryParam("search", search)
                        .queryParam("size", size)
                        .queryParam("page", page)
                        .toUriString(),
                headers,
                ReportResponseDto.class);
    }
}
