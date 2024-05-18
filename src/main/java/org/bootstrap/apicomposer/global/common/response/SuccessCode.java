package org.bootstrap.apicomposer.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // 2xx Success
    SUCCESS(200, "요청에 성공하였습니다."),
    CREATED(201, "생성되었습니다.");

    private final int code;
    private final String message;
}