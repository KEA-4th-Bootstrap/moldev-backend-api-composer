package org.bootstrap.apicomposer.domain.report.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.report.entity.ReportType;
import org.bootstrap.apicomposer.domain.report.service.ReportService;
import org.bootstrap.apicomposer.domain.user.service.UserService;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/api/compose/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/processed")
    public Mono<ApiResponse<?>> getReportListIsProcessed(@RequestParam(name = "type", required = false) ReportType reportType,
                                                         @RequestParam(name = "search", required = false) String search,
                                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                         ServerHttpRequest request) {
        return reportService.getReportListIsProcessed(reportType, search, size, page, request);
    }

    @GetMapping("/detail/{contentId}")
    public Mono<ApiResponse<?>> getReportDetail(@RequestParam(name = "type") ReportType reportType,
                                                @PathVariable String contentId,
                                                ServerHttpRequest request) {
        return reportService.getReportDetail(reportType, contentId, request);
    }
}
