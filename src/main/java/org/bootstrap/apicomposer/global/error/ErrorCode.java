package org.bootstrap.apicomposer.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "어진아 요청 좀 잘 보내"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "어진아 요청 좀 잘 보내"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "어진아 요청 좀 잘 보내"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "어진아 요청 좀 잘 보내"),
    CONFLICT(HttpStatus.CONFLICT, 409, "이미 존재하는 리소스입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500,"서버 내부 오류입니다.");

    private final HttpStatus code;
    private final Integer status;
    private final String message;
}
