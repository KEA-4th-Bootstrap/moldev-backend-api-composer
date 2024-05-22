package org.bootstrap.apicomposer.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, 400,"어진아 요청 좀 잘 보내");

    private final HttpStatus code;
    private final Integer status;
    private final String message;
}
