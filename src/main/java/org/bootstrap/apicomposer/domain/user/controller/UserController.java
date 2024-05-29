package org.bootstrap.apicomposer.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.user.service.UserService;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/compose/user")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/ban/{reportId}")
    public Mono<ApiResponse<?>> banUserByReport(@PathVariable Long reportId,
                                                @RequestBody Object requestBody,
                                                ServerHttpRequest request) {
        return userService.banUserByReport(reportId, requestBody, request);
    }
}
