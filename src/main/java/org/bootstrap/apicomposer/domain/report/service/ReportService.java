package org.bootstrap.apicomposer.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.report.dto.BanDaysRequestDto;
import org.bootstrap.apicomposer.domain.report.dto.ReportResponseDto;
import org.bootstrap.apicomposer.domain.report.dto.ReportWithBandaysListResponseDto;
import org.bootstrap.apicomposer.domain.report.dto.ReportWithBandaysResponseDto;
import org.bootstrap.apicomposer.domain.report.entity.ReportType;
import org.bootstrap.apicomposer.domain.report.helper.ReportHelper;
import org.bootstrap.apicomposer.domain.report.vo.ReportResponseVo;
import org.bootstrap.apicomposer.domain.user.dto.response.BanDaysResponseDto;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.bootstrap.apicomposer.global.common.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportHelper reportHelper;
    private final UserHelper userHelper;

    public Mono<ApiResponse<?>> getReportListIsProcessed(ReportType reportType, String search, Integer size, Integer page, ServerHttpRequest request) {
        Mono<ResponseEntity<ReportResponseDto>> reportProcessed = reportHelper.getReportProcessed(reportType, search, size, page, request.getHeaders());
        return reportProcessed.flatMap(result -> {
            List<Long> reportIds = result.getBody().reportResponseVo()
                    .stream()
                    .map(ReportResponseVo::reportId)
                    .toList();
            BanDaysRequestDto banDaysRequestDto = BanDaysRequestDto.of(reportIds);
            return userHelper.getUserBanDaysInfo(banDaysRequestDto, request.getHeaders())
                    .flatMap(banDaysResponseDto -> {
                        List<ReportWithBandaysResponseDto> reportWithBandaysResponseList = result.getBody().reportResponseVo()
                                .stream()
                                .map(reportResponseVo -> {
                                    Integer bandays = banDaysResponseDto.getBody()
                                            .banDaysResponseDto()
                                            .stream()
                                            .filter(banDaysListResponse -> banDaysListResponse.reportId().equals(reportResponseVo.reportId()))
                                            .findFirst()
                                            .map(BanDaysResponseDto::banDays)
                                            .orElse(0);
                                    return ReportWithBandaysResponseDto.of(reportResponseVo, bandays);
                                })
                                .toList();
                        return Mono.just(ApiResponse.of(SuccessCode.SUCCESS, ReportWithBandaysListResponseDto.of(reportWithBandaysResponseList, result.getBody().pageInfo())));
                    });
        });
    }
}
