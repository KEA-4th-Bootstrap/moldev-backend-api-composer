package org.bootstrap.apicomposer.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    // 1000 auth 오류

    // 4xx Client Error
    BAD_REQUEST(400,  "잘못된 요청입니다."),
    UNAUTHORIZED(401,  "인증되지 않은 사용자입니다."),
    FORBIDDEN(403,  "접근 권한이 없습니다."),
    NOT_FOUND(404,  "요청하신 자원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405,  "허용되지 않은 메소드입니다."),
    CONFLICT(409,  "요청하신 자원이 충돌하였습니다."),
    UNSUPPORTED_MEDIA_TYPE(415,  "지원되지 않는 미디어 타입입니다."),

    // 5xx Server Error
    INTERNAL_SERVER_ERROR(500,  "서버 내부 오류입니다."),
    BAD_GATEWAY(502,  "게이트웨이 오류입니다."),
    SERVICE_UNAVAILABLE(503,  "서비스를 사용할 수 없습니다."),
    GATEWAY_TIMEOUT(504,  "게이트웨이 타임아웃입니다.");

    private final int code;
    private final String message;
}